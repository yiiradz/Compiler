package cminuscompiler;

import java.io.*;

public interface Compiler {
  public void compile(String filePrefix) throws IOException;
}
