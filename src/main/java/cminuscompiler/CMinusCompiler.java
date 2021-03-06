package cminuscompiler;

import x64codegen.X64AssemblyGenerator;
import java.util.*;
import java.io.*;
import optimizer.*;
import x86codegen.*;
import x64codegen.*;
import dataflow.*;
import lowlevel.CodeItem;

public class CMinusCompiler implements Compiler {

    public static HashMap globalHash = new HashMap(); // this is the global symbol table. all you care about is the variable name not its value
    private static boolean genX64Code = false;

    public CMinusCompiler() {
    }

    public static void setGenX64Code(boolean useX64) {
        genX64Code = useX64;
    }
    public static boolean getGenX64Code() {
        return genX64Code;
    }

    @Override
    public void compile(String filePrefix) throws FileNotFoundException, IOException {

        String fileName = filePrefix + ".c";
        BufferedReader br = new BufferedReader(new FileReader (fileName));
        
        //Matthew's filepath
        //String filename = "/Users/matthewoh/NetBeansProjects/Compiler/src/main/java/cminuscompiler/output.ast";
        
        //Yayira's filepath
        String filename = "/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/output.ast";
        BufferedWriter w = new BufferedWriter(new FileWriter(filename));
        try {
            Parser myParser = new CMinusParser(br); //TODO: adapt this line to make our parser and scanner

            Program parseTree = myParser.parse(); // this calls p2
            parseTree.printTree(w); // TODO: we use a bufferedreader in our print and that might be an issue
            w.close();
            CodeItem lowLevelCode = parseTree.genLLCode(); // this is p3 this should call program genllcode
            //program llcode for everyy decl call gencode
            
            
            // this is all G's backend
            fileName = filePrefix + ".ll";
            PrintWriter outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            int optiLevel = 2;
            LowLevelCodeOptimizer lowLevelOpti =
                    new LowLevelCodeOptimizer(lowLevelCode, optiLevel);
            lowLevelOpti.optimize();

            fileName = filePrefix + ".opti";
            outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            if (genX64Code) {
                X64CodeGenerator x64gen = new X64CodeGenerator(lowLevelCode);
                x64gen.convertToX64();
            }
            else {
                X86CodeGenerator x86gen = new X86CodeGenerator(lowLevelCode);
                x86gen.convertToX86();
            }
            fileName = filePrefix + ".x86";
            outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

//    lowLevelCode.printLLCode(null);

            // simply walks functions and finds in and out edges for each BasicBlock
            ControlFlowAnalysis cf = new ControlFlowAnalysis(lowLevelCode);
            cf.performAnalysis();
//    cf.printAnalysis(null);

            // performs DU analysis, annotating the function with the live range of
            // the value defined by each oper (some merging of opers which define
            // same virtual register is done)
//    DefUseAnalysis du = new DefUseAnalysis(lowLevelCode);
//    du.performAnalysis();
//    du.printAnalysis();

            LivenessAnalysis liveness = new LivenessAnalysis(lowLevelCode);
            liveness.performAnalysis();
            liveness.printAnalysis();

            if (genX64Code) {
                int numRegs = 15;
                X64RegisterAllocator regAlloc = new X64RegisterAllocator(lowLevelCode,
                        numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                X64AssemblyGenerator assembler =
                        new X64AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateX64Assembly();
                outFile.close();
            }
            else {
                int numRegs = 7;
                X86RegisterAllocator regAlloc = new X86RegisterAllocator(lowLevelCode,
                        numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                X86AssemblyGenerator assembler =
                        new X86AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateAssembly();
                outFile.close();
            }

        } catch (IOException ioe) {
        }

    }

    public static void main(String[] args) throws IOException {
        //Matthew's filepath
        //String filePrefix = "/Users/matthewoh/NetBeansProjects/Compiler/src/main/java/cminuscompiler/test";
        
        //Yayira's filepath
        String filePrefix = "/Users/yiradz/College/SENIOR_sem2/compiler/compiler/src/main/java/cminuscompiler/test";
        CMinusCompiler myCompiler = new CMinusCompiler();
        myCompiler.setGenX64Code(true);
        myCompiler.compile(filePrefix);
    }
}
