// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: JSON_lexer.jflex

//Configuración
package Backend.Analizadores;
import java_cup.runtime.*;
import Backend.Objetos.Token;
import static Backend.Analizadores.JSON_ParserSym.*;
import Backend.Manejadores.ManejadorErrores;
//import Objetos.ReporteError;//yo supongo que si se debe importar para usar el eqq de ctes static, aunque sea kotlin... solo era para probar que si jala cosas de kotlin en Java xD


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class Lexer_JSON implements java_cup.runtime.Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;
  public static final int SSTRING = 2;
  public static final int ERROR = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2, 2
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\1\u0100\u10fe\u0200";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\1\1\3\22\0\1\1"+
    "\1\0\1\4\10\0\1\5\1\6\3\0\12\7\1\6"+
    "\6\0\1\10\1\11\1\12\1\13\1\14\1\15\2\0"+
    "\1\16\2\0\1\17\1\20\1\21\1\22\1\23\1\0"+
    "\1\24\1\25\1\26\1\27\1\30\1\0\1\31\2\0"+
    "\1\6\1\32\1\6\3\0\1\10\1\11\1\12\1\13"+
    "\1\14\1\15\2\0\1\16\2\0\1\17\1\20\1\21"+
    "\1\22\1\23\1\0\1\24\1\25\1\26\1\27\1\30"+
    "\1\0\1\31\2\0\1\6\1\0\1\6\262\0\2\16"+
    "\115\0\1\25\u0180\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[768];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\2\2\1\3\1\4\1\5\10\1\1\6"+
    "\1\7\1\10\2\11\14\0\1\12\1\13\1\14\1\15"+
    "\12\0\1\16\10\0\1\17\7\0\1\20\1\21\1\0"+
    "\1\22\3\0\1\23\3\0\1\24\1\25\7\0\1\26"+
    "\1\0\1\27\1\30";

  private static int [] zzUnpackAction() {
    int [] result = new int[89];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\33\0\66\0\121\0\121\0\154\0\121\0\121"+
    "\0\207\0\242\0\275\0\330\0\363\0\u010e\0\u0129\0\u0144"+
    "\0\u015f\0\u017a\0\u0195\0\u01b0\0\121\0\u01cb\0\u01e6\0\u0201"+
    "\0\u021c\0\u0237\0\u0252\0\u026d\0\u0288\0\u02a3\0\u02be\0\u02d9"+
    "\0\u0195\0\u02f4\0\121\0\121\0\121\0\121\0\u030f\0\u032a"+
    "\0\u0345\0\u0360\0\u037b\0\u0396\0\u03b1\0\u03cc\0\u03e7\0\u0402"+
    "\0\121\0\u041d\0\u0438\0\u0453\0\u046e\0\u0489\0\u04a4\0\u04bf"+
    "\0\u04da\0\121\0\u04f5\0\u0510\0\u052b\0\u0546\0\u0561\0\u057c"+
    "\0\u0597\0\121\0\121\0\u05b2\0\121\0\u05cd\0\u05e8\0\u0603"+
    "\0\121\0\u061e\0\u0639\0\u0654\0\121\0\121\0\u066f\0\u068a"+
    "\0\u06a5\0\u06c0\0\u06db\0\u06f6\0\u0711\0\121\0\u072c\0\121"+
    "\0\121";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[89];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\2\5\1\6\1\7\1\4\1\10\1\11\2\4"+
    "\1\12\2\4\1\13\2\4\1\14\1\15\1\4\1\16"+
    "\1\4\1\17\1\20\1\4\1\21\2\4\2\22\2\4"+
    "\1\23\25\22\1\24\1\4\2\25\1\26\2\4\1\10"+
    "\24\4\35\0\1\5\37\0\1\11\42\0\1\27\2\0"+
    "\1\30\37\0\1\31\17\0\1\32\40\0\1\33\20\0"+
    "\1\34\34\0\1\35\34\0\1\36\1\0\1\37\24\0"+
    "\1\40\22\0\2\22\3\0\25\22\2\0\3\41\1\0"+
    "\1\42\31\0\1\43\14\0\1\44\2\0\1\45\1\0"+
    "\1\46\6\0\1\25\40\0\1\47\42\0\1\50\33\0"+
    "\1\51\37\0\1\52\24\0\1\53\36\0\1\54\30\0"+
    "\1\55\41\0\1\56\24\0\1\57\33\0\1\60\7\0"+
    "\3\42\1\61\53\0\1\62\21\0\1\63\30\0\1\64"+
    "\42\0\1\65\21\0\1\66\31\0\1\67\46\0\1\70"+
    "\34\0\1\71\26\0\1\72\26\0\1\73\30\0\1\74"+
    "\37\0\1\75\27\0\1\76\27\0\1\77\43\0\1\100"+
    "\26\0\1\101\26\0\1\102\40\0\1\103\20\0\1\104"+
    "\47\0\1\105\33\0\1\106\26\0\1\107\32\0\1\110"+
    "\24\0\1\111\32\0\1\112\27\0\1\113\31\0\1\114"+
    "\43\0\1\115\36\0\1\116\33\0\1\117\23\0\1\120"+
    "\37\0\1\121\32\0\1\122\22\0\1\123\34\0\1\124"+
    "\36\0\1\125\35\0\1\126\27\0\1\127\35\0\1\130"+
    "\32\0\1\131\5\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1863];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\2\11\1\1\2\11\14\1\1\11\1\1\14\0"+
    "\4\11\12\0\1\11\10\0\1\11\7\0\2\11\1\0"+
    "\1\11\3\0\1\11\3\0\2\11\7\0\1\11\1\0"+
    "\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[89];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */
    ManejadorErrores manejadorErrores;

    boolean requeriaCompania = false;
    Token tokenAnterior = null;

    StringBuffer contenido = new StringBuffer();
  
    private Symbol symbol(int tipo, String valor, boolean conCompania){//Dejé el valor como string, como no requiero que alguno de los lexemas sea diferente...
        Token tokenActual = new Token ((yyline+1), (yycolumn+1), valor, (conCompania)?tokenAnterior:null);        

        if(tokenAnterior != null && requeriaCompania){
            tokenAnterior.setSiguiente(tokenActual);
        }

        tokenAnterior = tokenActual;
        requeriaCompania = conCompania;
        return new Symbol(tipo, yyline+1, yycolumn+1, tokenActual);
    }

    private Symbol acccionReservada(int tipo){//por el momneot es void xD    
        System.out.println("[L] reservada: "+ yytext());                

        return symbol(tipo, yytext(), false);    
    }    

    private Symbol accionSimbolosAceptados(){        
        if(yystate() == ERROR){
            accionParadaParaError();
        }

        System.out.println("[L] símbolo "+ yytext() +" T: " +((yytext().equals(":"))?"DOS_PUNTOS":((yytext().equals(","))?"COMA":((yytext().equals("{"))?"LLAVE_A":((yytext().equals("}"))?"LLAVE_C":((yytext().equals("["))?"CORCHETE_A":"CORCHETE_C"))))));        
        return symbol(((yytext().equals(":"))?DOS_PUNTOS:((yytext().equals(","))?COMA:((yytext().equals("{"))?LLAVE_A:((yytext().equals("}"))?LLAVE_C:((yytext().equals("["))?CORCHETE_A:CORCHETE_C))))), yytext(), false);    
    }//por si acaso miras que si te es posible add SA a ERROR sin generar problemas al formar los tokens aquí y analizar las RP en el parser
   
    private void accionProcesarError(){
        if(yystate() != ERROR){//no coloco tb a CADENA, porque se supone que no debería hacer match con el [^] cuando esté dentro de ese estado léxico...
            //estadoAnteriorError = yystate();//esta es la alternativa a la que me refiero en axnParadaError, para dar msje cuando un string tiene error...
            contenido.setLength(0);//no provoca problemas el usar la variable string que tb usa SSTRING para concatenar, puesto que al estar en ese estado no se entrará aquí puesto que esta expre reg tiene la menor precedencia y las reglas de allá impiden que este caso suceda... puesto que se absorben todos los caracteres posibles hasta llegar a la otra "
            yypushback(yylength());//iba a colocar 1, en lugar de yylength pero no se si la unidad de medida varíe porque podría se que cada caracter tenga un tamaño diferente dependiendo de la cdad de bits que requiera para ser plasmado p.ej
            yybegin(ERROR);
        }else if(yystate() == ERROR){
            contenido.append(yytext());
        }
    }

    private void accionParadaParaError(){//aquí es donde se imprime todo lo concatenado que se clasificó como error...          
        System.out.println("[L] error: "+ contenido.toString() + ((contenido.length() != 0)?" INVALID WORD":"Las cadenas solo pueden ocupar una línea")+"\n");
        manejadorErrores.setError(new Token((yyline+1), (yycolumn+1), contenido.toString(), null), ((contenido.length() != 0)?false:true));//aquí no se requiere de un token previo... y esta vez nisiqueira en las operaciones xD

        yybegin(YYINITIAL);//ese operador ternario lo puse para que se justifique el hecho de que después de error no se muestre el contenido errado, puesto que contenido va a tener length = 0 si el error surgió en STRING [puesto que se llegará a error cuando haya salto de línea o retorno de carro no explícito] entonces puedo utilizar eso para personalizar el msje [cabe reslatar que si el error surge en el YYINI, siempre tendrá maś de algo contenido, puesto que desde ese estado se puede llegar a error si se encuentra con algo que no es aceptado y ahí el \n y \r son ignorados, es decir técnicamente aceptados xD]
    }//si la justificación por la cual uso el operador ternario no funciona, entonces guarda el stado y si ese es == SSTRING entonces pones ese msje xD, ahí si el msje estaría correcto siempre xD

    public void setInfoNecesaria(ManejadorErrores elManejadorErrores){
        manejadorErrores = elManejadorErrores;
    }//el RESULT ya tendrá seteada la clase respectiva sin problemas xD


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer_JSON(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  @Override  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return new java_cup.runtime.Symbol(JSON_ParserSym.EOF); }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { accionProcesarError();
            }
            // fall through
          case 25: break;
          case 2:
            { /*se ignora*/
            }
            // fall through
          case 26: break;
          case 3:
            { contenido.setLength(0); yybegin(SSTRING);
            }
            // fall through
          case 27: break;
          case 4:
            { return accionSimbolosAceptados();
            }
            // fall through
          case 28: break;
          case 5:
            { System.out.println("[L] numero: "+ yytext());return symbol(NUMERO, yytext(), false);
            }
            // fall through
          case 29: break;
          case 6:
            { contenido.append( yytext());
            }
            // fall through
          case 30: break;
          case 7:
            { yybegin(YYINITIAL);System.out.println("[L] cadena: "+ contenido.toString() + " T: "+CADENA);return symbol(CADENA, new String(contenido), false);
            }
            // fall through
          case 31: break;
          case 8:
            { contenido.append('\\');
            }
            // fall through
          case 32: break;
          case 9:
            { accionParadaParaError();
            }
            // fall through
          case 33: break;
          case 10:
            { contenido.append('\"');
            }
            // fall through
          case 34: break;
          case 11:
            { contenido.append('\n');
            }
            // fall through
          case 35: break;
          case 12:
            { contenido.append('\r');
            }
            // fall through
          case 36: break;
          case 13:
            { contenido.append('\t');
            }
            // fall through
          case 37: break;
          case 14:
            { /*no se append, puesto que solo se desea almacenar el contenido*/
            }
            // fall through
          case 38: break;
          case 15:
            { return acccionReservada(TIPO);
            }
            // fall through
          case 39: break;
          case 16:
            { return acccionReservada(SCORE);
            }
            // fall through
          case 40: break;
          case 17:
            { return acccionReservada(TEXTO);
            }
            // fall through
          case 41: break;
          case 18:
            { return acccionReservada(CLASES);
            }
            // fall through
          case 42: break;
          case 19:
            { return acccionReservada(NOMBRE);
            }
            // fall through
          case 43: break;
          case 20:
            { return acccionReservada(FUNCION);
            }
            // fall through
          case 44: break;
          case 21:
            { return acccionReservada(METODOS);
            }
            // fall through
          case 45: break;
          case 22:
            { return acccionReservada(VARIABLES);
            }
            // fall through
          case 46: break;
          case 23:
            { return acccionReservada(PARAMETROS);
            }
            // fall through
          case 47: break;
          case 24:
            { return acccionReservada(COMENTARIOS);
            }
            // fall through
          case 48: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
