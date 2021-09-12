package org.openblock.creator.impl.custom.clazz.reader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.OpenBlockCreator;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.clazz.type.IType;
import org.openblock.creator.code.clazz.type.SpecifiedGenericType;
import org.openblock.creator.code.clazz.type.VoidType;
import org.openblock.creator.code.function.IFunction;
import org.openblock.creator.code.line.Line;
import org.openblock.creator.code.line.MultiLine;
import org.openblock.creator.code.line.primitive.StringConstructor;
import org.openblock.creator.code.line.returning.ReturnLine;
import org.openblock.creator.code.statement.Statement;
import org.openblock.creator.code.variable.IVariable;
import org.openblock.creator.code.variable.field.Field;
import org.openblock.creator.code.variable.field.InitiatedField;
import org.openblock.creator.code.variable.field.UninitiatedField;
import org.openblock.creator.code.variable.parameter.Parameter;
import org.openblock.creator.code.variable.parameter.WritableParameter;
import org.openblock.creator.impl.custom.clazz.AbstractCustomClass;
import org.openblock.creator.impl.custom.clazz.CustomClassBuilder;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;
import org.openblock.creator.impl.custom.function.method.CustomMethod;
import org.openblock.creator.project.Project;

import java.util.*;
import java.util.regex.Pattern;

public class CustomClassReader {

    private final List<String> lines = new ArrayList<>();
    private final List<String> importLines = new ArrayList<>();
    private final CustomClassBuilder builder = new CustomClassBuilder();
    private int classStartsOn;

    @Deprecated
    public CustomClassReader() {
        this(new String[0]);
    }

    public CustomClassReader(String... clazz) {
        this(Arrays.asList(clazz));
    }

    public CustomClassReader(Collection<String> lines) {
        if (lines.isEmpty()) {
            throw new RuntimeException();
        }
        this.lines.addAll(lines);
    }

    public AbstractCustomClass readStageOne(Project<AbstractCustomClass> project) {
        readPackage();
        readImports();
        readClassStart(project);
        return this.builder.build();
    }

    public AbstractCustomClass readStageTwo(Project<AbstractCustomClass> project, AbstractCustomClass target) {
        readFieldsInit(target, project);
        readMethodsCodeInit(target, project);
        return target;
    }

    public AbstractCustomClass readStageThree(Project<AbstractCustomClass> project, AbstractCustomClass target) {
        readFieldsCode(target, project);
        readMethodsCode(target, project);
        return target;
    }

    private void readImports() {
        for (String s : this.lines) {
            String line = s.trim();
            if (line.startsWith("import ")) {
                this.importLines.add(line.substring(7, line.length() - 1));
            }
            if (line.endsWith("{")) {
                return;
            }
        }
    }

    private List<String> getCodeBlock(final int lineStart, int bracesFound) {
        return getCodeBlock(this.lines, lineStart, bracesFound);
    }

    private List<String> getCodeBlock(List<String> block, final int lineStart, int bracesFound) {
        boolean inString = false;
        for (int a = lineStart; a < block.size(); a++) {
            String line = block.get(a);
            for (int i = 0; i < line.length(); i++) {
                char at = line.charAt(i);

                if (!inString) {
                    if (at == '{') {
                        bracesFound++;
                    }
                    if (at == '}') {
                        bracesFound--;
                        if (bracesFound == 0) {
                            return block.subList(lineStart, a);
                        }
                    }
                }

                if (at == '"') {
                    if (i > 0) {
                        if (line.charAt(i - 1) == '\\') {
                            continue;
                        }
                    }
                    inString = !inString;
                }
            }
        }
        throw new RuntimeException("Could not find block end");
    }

    private List<Codeable> readCodeBlock(List<String> block, List<IVariable> variables, AbstractCustomClass clazz, Project<AbstractCustomClass> project) {
        List<Codeable> ret = new ArrayList<>();
        for (int A = 0; A < block.size(); A++) {
            String line = block.get(A);
            if (line.endsWith(";")) {
                return Collections.singletonList(this.readChainedCode(line, variables, clazz, project));
            } else {
                List<String> lineBlockLines = getCodeBlock(block, A + 1, 1);
                List<Codeable> lineBlock = readCodeBlock(lineBlockLines, variables, clazz, project);
                Statement statement = readStatement(line, lineBlock, clazz, project);
                ret.add(statement);
            }
        }
        return ret;
    }

    private Line readNoneChainedCode(@NotNull String line, @Nullable Codeable previous, @NotNull Collection<IVariable> variables, @NotNull AbstractCustomClass clazz, @NotNull Project<AbstractCustomClass> project) {
        line = line.trim();
        if (line.endsWith(";")) {
            line = line.substring(0, line.length() - 1);
        }

        final String finalLine = line;

        Optional<IVariable> opVariable = variables.stream().filter(var -> var.getName().equals(finalLine)).findAny();
        if (opVariable.isPresent()) {
            return opVariable.get().createCaller();
        }
        Optional<Field> opField = clazz.getFields().stream().filter(var -> var.getName().equals(finalLine)).findAny();
        if (opField.isPresent()) {
            return opField.get().createCaller();
        }
        if (line.startsWith("\"") && line.endsWith("\"")) {
            return new StringConstructor(line.substring(1, line.length() - 1));
        }

        if (line.startsWith("return ")) {
            Line result = readNoneChainedCode(line.substring(7), previous, variables, clazz, project);
            if (result instanceof Returnable.ReturnableLine resultLine) {
                return new ReturnLine(resultLine);
            }
            throw new IllegalStateException("Line does not return a value: '" + line.substring(7) + "'");
        }
        throw new IllegalStateException("Unknown line of '" + line + "'");
    }

    private Codeable readChainedCode(String line, Collection<IVariable> variables, AbstractCustomClass clazz, Project<AbstractCustomClass> project) {
        Codeable codeable = null;
        StringBuilder buffer = new StringBuilder();
        int braces = 0;
        for (int at = 0; at < line.length(); at++) {
            char charAt = line.charAt(at);
            if (charAt == '(') {
                braces++;
            }
            if (charAt == ')') {
                braces--;
            }
            if (charAt == '.' && braces == 0) {
                String current = buffer.toString();
                if (current.startsWith("(") && current.endsWith(")")) {
                    Codeable toAdd = readChainedCode(current, variables, clazz, project);
                    if (codeable instanceof Caller.ParameterCaller parameterCall) {
                        parameterCall.getParameters().add(toAdd);
                    } else if (codeable instanceof MultiLine multiLine && toAdd instanceof Returnable.ReturnableLine lineToAdd) {
                        multiLine.getLines().add(lineToAdd);
                    }
                } else {
                    Line toAdd = readNoneChainedCode(current, codeable, variables, clazz, project);
                    if (codeable == null) {
                        codeable = toAdd;
                    } else if (codeable instanceof MultiLine multiLine && toAdd instanceof Returnable.ReturnableLine toAddLine) {
                        multiLine.getLines().add(toAddLine);
                    }
                }
                buffer = new StringBuilder();
            }
            buffer.append(charAt);
        }
        String current = buffer.toString();
        if (current.startsWith("(") && current.endsWith(")")) {
            Codeable toAdd = readChainedCode(current, variables, clazz, project);
            if (codeable instanceof Caller.ParameterCaller parameterCall) {
                parameterCall.getParameters().add(toAdd);
            } else if (codeable instanceof MultiLine multiLine && toAdd instanceof Returnable.ReturnableLine lineToAdd) {
                multiLine.getLines().add(lineToAdd);
            }
        } else {
            Line toAdd = readNoneChainedCode(current, codeable, variables, clazz, project);
            if (codeable == null) {
                codeable = toAdd;
            } else if (codeable instanceof MultiLine multiLine && toAdd instanceof Returnable.ReturnableLine toAddLine) {
                multiLine.getLines().add(toAddLine);
            }
        }
        return codeable;
    }

    private Statement readStatement(String statementLine, List<Codeable> block, AbstractCustomClass clazz, Project<AbstractCustomClass> project) {
        statementLine = statementLine.trim();
        if (statementLine.startsWith("if")) {
            //DO IF
        }
        if (statementLine.startsWith("while")) {
            //DO WHILE
        }
        if (statementLine.startsWith("do")) {
            //DO DO WHILE
        }
        if (statementLine.startsWith("switch")) {
            //DO SWITCH STATEMENT
        }
        throw new IllegalStateException("Unknown statement from line '" + statementLine + "'");
    }

    private void readMethodsCode(AbstractCustomClass clazz, Project<AbstractCustomClass> project) {
        List<Integer> methodStartLineNumbers = new ArrayList<>();
        for (int i = this.classStartsOn + 1; i < this.lines.size(); i++) {
            String line = this.lines.get(i);
            String whitespaceRemoved = line.trim().replaceAll(" ", "");
            if (whitespaceRemoved.contains(clazz.getName() + "(")) {
                //constructor
                continue;
            }
            if (whitespaceRemoved.endsWith("){}")) {
                //find better check for this
                methodStartLineNumbers.add(i);
                continue;
            }
            if (whitespaceRemoved.endsWith("){")) {
                //find better check for this
                methodStartLineNumbers.add(i);
                i = i + this.getCodeBlock(i + 1, 1).size();
                continue;
            }
            if (whitespaceRemoved.endsWith(");")) {
                //find better check for this
                methodStartLineNumbers.add(i);
            }
        }
        for (IFunction function : clazz.getFunctions()) {
            Integer lineNumber = null;
            for (int methodStart : methodStartLineNumbers) {
                String methodLinedTrimmed = this.lines.get(methodStart).trim();
                if (!methodLinedTrimmed.contains(function.getName())) {
                    continue;
                }
                lineNumber = methodStart;
                break;
            }
            if (lineNumber == null) {
                continue;
            }
            List<String> lines = this.getCodeBlock(lineNumber + 1, 1);
            List<Codeable> codableBlock = this.readCodeBlock(lines, new ArrayList<>(function.getParameters()), clazz, project);
            function.getCodeBlock().addAll(codableBlock);
        }
    }

    private void readMethodsCodeInit(AbstractCustomClass clazz, Project<AbstractCustomClass> project) {
        List<Integer> methodStartLineNumbers = new ArrayList<>();
        for (int i = this.classStartsOn + 1; i < this.lines.size(); i++) {
            String line = this.lines.get(i);

            String whitespaceRemoved = line.trim().replaceAll(" ", "");
            if (line.trim().length() == whitespaceRemoved.length()) {
                continue;
            }
            if (!whitespaceRemoved.contains("(")) {
                continue;
            }
            if (!whitespaceRemoved.contains(")")) {
                continue;
            }
            Integer parameterStart = null;
            Integer parameterEnd = null;
            for (int a = 0; a < line.length(); a++) {
                char charAt = line.charAt(a);
                if (parameterStart == null && charAt == '(') {
                    parameterStart = a;
                    continue;
                }
                if (parameterEnd == null && charAt == ')') {
                    parameterEnd = a;
                    continue;
                }
                if (parameterStart != null && charAt == '(') {
                    parameterStart = null;
                    break;
                }
                if (parameterEnd != null && charAt == ')') {
                    parameterStart = null;
                    break;
                }
            }
            if (parameterStart == null || parameterEnd == null) {
                continue;
            }
            String paramString = line.substring(parameterStart + 1, parameterEnd);
            if (paramString.length() != 0) {
                if (paramString.split(",").length > 0) {
                    boolean has = true;
                    for (String param : paramString.split(",")) {
                        if (!param.contains(" ")) {
                            has = false;
                            break;
                        }
                    }
                    if (!has) {
                        continue;
                    }
                } else if (paramString.split(" ").length > 1) {
                    continue;
                }
            }
            if (whitespaceRemoved.contains(clazz.getName() + "(")) {
                //constructor
                continue;
            }
            if (whitespaceRemoved.endsWith("){}")) {
                //Find better check for this
                methodStartLineNumbers.add(i);
                continue;
            }
            if (whitespaceRemoved.endsWith("){")) {
                //Find better check for this
                methodStartLineNumbers.add(i);
                i = i + this.getCodeBlock(i + 1, 1).size();
                continue;
            }
            if (whitespaceRemoved.endsWith(");")) {
                //find better check for this
                methodStartLineNumbers.add(i);
            }
        }
        for (int lineNumber : methodStartLineNumbers) {
            String line = this.lines.get(lineNumber);
            Integer genericStart = null;
            Integer genericEnd = null;
            int genericIn = 0;
            int braceStart = 0;
            int braceEnd = 0;
            int braceIn = 0;
            for (int b = 0; b < line.length(); b++) {
                char at = line.charAt(b);
                if (at == '<') {
                    if (genericStart == null) {
                        genericStart = b;
                    }
                    genericIn++;
                }
                if (at == '>') {
                    genericEnd = b;
                    genericIn--;
                }
                if (at == '(') {
                    braceStart = b;
                    braceIn++;
                }
                if (at == ')') {
                    braceEnd = b;
                    braceIn--;
                    if (braceIn == 0) {
                        break;
                    }
                }
            }

            //handle generics

            //handle parameters
            String parameterSector = line.substring(braceStart + 1, braceEnd);
            String[] parameters = parameterSector.split(",");
            List<Parameter> methodParameters = new ArrayList<>();
            for (String pValue : parameters) {
                String[] split = pValue.split(" ");
                if (split.length < 2) {
                    continue;
                }
                boolean isFinal = false;
                if (split.length >= 3) {
                    for (int c = 0; c < split.length - 2; c++) {
                        String command = split[c];
                        if (command.equals("final")) {
                            isFinal = true;
                            break; //temp
                        }
                        //annotations
                    }
                }
                String clazzString = split[split.length - 2];
                String varString = split[split.length - 1];
                StatedReturnType clazzRead = readClass(clazzString, project);
                WritableParameter parameter = new WritableParameter(clazzRead, varString, isFinal);
                methodParameters.add(parameter);
            }

            //method pre line
            String methodInit = line.substring(0, braceStart);
            boolean isStatic = false;
            Visibility visibility = Visibility.PACKAGED;
            if (methodInit.contains(" static ")) {
                isStatic = true;
            }
            for (Visibility vis : Visibility.values()) {
                if (methodInit.contains(vis.getDisplayName())) {
                    visibility = vis;
                }
            }

            String[] split = methodInit.split(" ");
            if (split.length < 2) {
                throw new IllegalStateException("Could not split the spaces by 2 or more in: '" + methodInit + "' -> '" + line + "'");
            }
            String name = split[split.length - 1];
            String returnTypeString = split[split.length - 2];
            StatedReturnType returnType = readClass(returnTypeString, project);

            // method creation
            CustomMethod method = (CustomMethod) new CustomFunctionBuilder()
                    .setName(name)
                    .setReturnType(returnType)
                    .setVisibility(visibility)
                    .addParameters(methodParameters)
                    .setStatic(isStatic)
                    .build(clazz);

            //add to class
            clazz.getFunctions().add(method);
        }


    }

    private void readFieldsCode(AbstractCustomClass clazz, Project<AbstractCustomClass> project) {
        List<Integer> fieldLineNumbers = new ArrayList<>();
        for (int i = this.classStartsOn + 1; i < this.lines.size(); i++) {
            String line = this.lines.get(i).trim();
            if (line.length() == 0) {
                continue;
            }
            if (!line.contains(" = ")) {
                continue;
            }
            if (line.endsWith("{")) {
                break;
            }
            if (line.endsWith("}")) {
                break;
            }
            fieldLineNumbers.add(i);
        }
        List<Field> fields = clazz.getFields();

        new ArrayList<>(fields)
                .forEach(field -> {
                    Optional<Integer> opLineNumber = fieldLineNumbers
                            .stream()
                            .filter(line -> this.lines.get(line).split(" = ", 2)[0].contains(field.getName()))
                            .findAny();
                    if (opLineNumber.isEmpty()) {
                        return;
                    }
                    int lineNumber = opLineNumber.get();
                    String line = this.lines.get(lineNumber);
                    List<String> code = new ArrayList<>();
                    String trimmed = line.split(" = ")[1];
                    if (!line.endsWith(";")) {
                        int offset = 0;
                        if (line.endsWith("{")) {
                            offset++;
                        }
                        if (!trimmed.isBlank()) {
                            code.add(trimmed);
                        }
                        code.addAll(getCodeBlock(lineNumber + 1, offset));
                    } else {
                        code.add(trimmed);
                    }

                    List<Codeable> fieldValue = this.readCodeBlock(code, Collections.emptyList(), clazz, project);
                    if (fieldValue.isEmpty()) {
                        throw new IllegalStateException("Could not convert field codeblock of: \n" + String.join("\n", code));
                    }
                    if (fieldValue.size() == 1 && fieldValue.get(0) instanceof Returnable.ReturnableLine caller) {
                        InitiatedField initField = new InitiatedField(field.getVisibility(), caller, field.getName(), field.isFinal(), field.isStatic());
                        fields.remove(field);
                        fields.add(initField);
                        return;
                    }
                    if (fieldValue.size() > 1) {
                        throw new IllegalStateException("Cannot handle codeblocks in fields yet");
                    }
                    throw new IllegalStateException("could not handle code (" + fieldValue.size() + ") of: \n " + String.join("\n", code));
                });
    }

    private void readFieldsInit(AbstractCustomClass clazz, Project<AbstractCustomClass> project) {
        List<String> fieldLines = new ArrayList<>();
        for (int i = this.classStartsOn + 1; i < this.lines.size(); i++) {
            String line = this.lines.get(i).trim();
            if (line.length() == 0) {
                continue;
            }
            if (line.endsWith("{")) {
                break;
            }
            if (line.endsWith("}")) {
                break;
            }
            fieldLines.add(line);
        }

        for (String fieldLine : fieldLines) {
            String[] commands = fieldLine.split(" ");
            if (commands.length <= 0) {
                continue;
            }
            Visibility visibility = Visibility.PACKAGED;
            String name = null;
            StatedReturnType returnType = null;
            boolean isFinal = false;
            boolean isStatic = false;

            int target = 0;
            for (Visibility vis : Visibility.values()) {
                if (vis.getDisplayName().equals(commands[0])) {
                    visibility = vis;
                    target = 1;
                    break;
                }
            }

            for (; target < commands.length; target++) {
                String command = commands[target];
                if (command.equals("final")) {
                    isFinal = true;
                    continue;
                }
                if (command.equals("static")) {
                    isStatic = true;
                    continue;
                }
                if (command.equals("=")) {
                    break;
                }
                if (name == null && returnType != null) {
                    name = command;
                    continue;
                }
                if (returnType == null) {
                    try {
                        returnType = readClass(command, project);
                    } catch (Throwable e) {
                        throw new RuntimeException("Failed to load field: '" + fieldLine + "'", e);
                    }
                }
            }

            if (name == null || returnType == null) {
                throw new IllegalStateException("Could not get field from '" + fieldLine + "'");
            }

            if (name.endsWith(";")) {
                name = name.substring(0, name.length() - 1);
            }

            UninitiatedField field = new UninitiatedField(visibility, returnType, name, isFinal, isStatic);
            clazz.getFields().add(field);

        }
    }

    private StatedReturnType readClass(String clazz, Project<AbstractCustomClass> project) {
        if (clazz.equals("void")) {
            return new StatedReturnType(new VoidType(), false);
        }
        if (!clazz.contains(Pattern.quote("."))) {
            String finalClazz = clazz;
            Optional<String> opImp = this.importLines.stream().filter(l -> l.endsWith(finalClazz)).findAny();
            if (opImp.isPresent()) {
                clazz = opImp.get();
            }
        }
        boolean isArray = false;
        if (clazz.endsWith("[]")) {
            clazz = clazz.substring(0, clazz.length() - 2);
            isArray = true;
        } else if (clazz.endsWith("...")) {
            clazz = clazz.substring(0, clazz.length() - 3);
            isArray = true;
        }
        final String finalClazz = clazz;
        Optional<IClass> opClass = project
                .getClasses()
                .parallelStream()
                .filter(customClass -> customClass.getFullName().equals(finalClazz))
                .map(customClass -> (IClass) customClass)
                .findAny()
                .or(() -> {
                    try {
                        return Optional.of(OpenBlockCreator.getJavaClass(finalClazz));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Cannot find class of '" + finalClazz + "'.", e);
                    }
                });
        if (opClass.isEmpty()) {
            throw new IllegalStateException("Cannot find class of '" + finalClazz + "'.");
        }
        IClass clazz1 = opClass.get();
        return new StatedReturnType(new BasicType(clazz1), isArray);
    }

    private void readClassStart(Project<AbstractCustomClass> project) {
        int i = 0;
        for (; i < this.lines.size(); i++) {
            String line = this.lines.get(i).trim();
            if (line.endsWith("{")) {
                break;
            }
        }

        this.classStartsOn = i;

        String line = this.lines.get(i);
        for (Visibility vis : Visibility.values()) {
            if (line.startsWith(vis.getDisplayName())) {
                this.builder.setVisibility(vis);
                break;
            }
        }
        if (line.contains("final")) {
            this.builder.setFinal(true);
        }
        if (line.contains("abstract")) {
            this.builder.setAbstract(true);
        }

        String[] commands = line.split(" ");

        for (ClassType type : ClassType.values()) {
            int b = 0;
            boolean set = false;
            for (; b < commands.length; b++) {
                String command = commands[b];
                if (command.equals(type.getCodeReference().toLowerCase())) {
                    this.builder.setType(type);
                    set = true;
                    break;
                }
            }
            if (set) {
                this.builder.setName(commands[b + 1]);
                break;
            }
        }

        if (line.contains("extends")) {
            int a = 0;
            for (; a < commands.length; a++) {
                String command = commands[a];
                if (command.equals(" extends ")) {
                    break;
                }
            }
            String clazz = commands[a + 1];
            IType type = readClass(clazz, project).getType();
            if (type instanceof BasicType bType) {
                this.builder.setExtending(bType.getTargetClass());
            } else if (type instanceof SpecifiedGenericType gType) {
                this.builder.setExtending(gType.getGenerics());
            } else {
                throw new IllegalStateException("Can not set extending of " + clazz);
            }
        }
        if (lines.contains(" implements ")) {
            int a = 0;
            for (; a < commands.length; a++) {
                String command = commands[a];
                if (command.equals(" implements ")) {
                    break;
                }
            }
            for (int b = 0; b < commands.length; b++) {
                String clazz = commands[a + b];
                @NotNull IType type = readClass(clazz, project).getType();
                if (type instanceof BasicType bType) {
                    this.builder.setExtending(bType.getTargetClass());
                } else if (type instanceof SpecifiedGenericType gType) {
                    this.builder.setExtending(gType.getGenerics());
                } else {
                    throw new IllegalStateException("Can not set extending of " + clazz);
                }
            }

        }
    }

    private void readPackage() {
        if (this.lines.isEmpty()) {
            throw new IllegalStateException("No lines");
        }
        String packageLine = this.lines.get(0).trim();
        if (!packageLine.startsWith("package ")) {
            throw new IllegalStateException("no package tag");
        }
        if (!packageLine.endsWith(";")) {
            throw new IllegalStateException();
        }
        String packageName = packageLine.substring(8, packageLine.length() - 1);
        this.builder.setPackageLocation(packageName.split(Pattern.quote(".")));
    }


}
