/* SpecParser.java */
/* Generated By:JavaCC: Do not edit this line. SpecParser.java */
package us.kbase.jkidl;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import us.kbase.kidl.KbAuthdef;
import us.kbase.kidl.KbFuncdef;
import us.kbase.kidl.KbList;
import us.kbase.kidl.KbMapping;
import us.kbase.kidl.KbModule;
import us.kbase.kidl.KbModuleComp;
import us.kbase.kidl.KbParameter;
import us.kbase.kidl.KbScalar;
import us.kbase.kidl.KbStruct;
import us.kbase.kidl.KbStructItem;
import us.kbase.kidl.KbTuple;
import us.kbase.kidl.KbType;
import us.kbase.kidl.KbTypedef;
import us.kbase.kidl.KbUnspecifiedObject;
import us.kbase.kidl.KidlParseException;

/**
 * Do not change this file. It's automatically generated based on src/kbase/jkidl/SpecParser.jj
 * using shell script in javacc/spec_javacc.sh . So please change parsing syntax and semantics
 * in SpecParser.jj .
 * @author rsutormin
 */
@SuppressWarnings({"unused", "serial"})
public class SpecParser implements SpecParserConstants {
        static ThreadLocal<String> lastComment = new ThreadLocal<String>();
        static ThreadLocal<Integer> lastCommentEndLine = new ThreadLocal<Integer>();

    public static void main(String args[]) throws Exception {
        String fileName = null;
        if (args.length < 1) {
            System.out.println("Usage: <program> <spec-file>");
                return;
        }
        fileName = args[0];
        SpecParser p = null;
        try {
            p = new SpecParser(new DataInputStream(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found.");
            return;
        }
        IncludeProvider ip = new StaticIncludeProvider();
        Map<?,?> root = parseAsJson(p, ip);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                mapper.writeValue(System.out, root);
        }

    public static Map<?,?> parseAsJson(SpecParser p, IncludeProvider ip) throws ParseException {
        lastComment.set(null);
        Map<String, KbModule> root = p.SpecStatement(ip);
        Map<String,List<Object>> ret = new LinkedHashMap<String, List<Object>>();
        for (KbModule module : root.values()) {
                List<Object> modList = ret.get(module.getServiceName());
                if (modList == null) {
                        modList = new ArrayList<Object>();
                        ret.put(module.getServiceName(), modList);
                }
                modList.add(module.toJson());
        }
        return ret;
    }

    public void generateParseException(KidlParseException t) throws ParseException {
        generateParseException(t.getMessage());
    }

    public void generateParseException(String message) throws ParseException {
        throw new ParseException("Error at line " + token.beginLine + ", column " + token.beginColumn + ": " + message);
    }

    public String getLastComment(Token first) {
        String comment = lastComment.get();
        lastComment.set(null);
        if (comment == null)
                return "";
        //if (first.beginLine > lastCommentEndLine.get() + 1)
        //	return "";
        return Utils.trim(comment);
    }

/**
 * Main parsing method. It iterates over includes and after that over modules.
 */
  final public Map<String, KbModule> SpecStatement(IncludeProvider ip) throws ParseException {Map<String, KbModule> ret = new LinkedHashMap<String, KbModule>();
  Map<String, KbModule> includes = null;
lastComment.set(null);
    includes = IncludeList(ip);
    ret = ModuleList(includes);
    jj_consume_token(0);
{if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

/**
 * Method iterates over includes.
 */
  final public Map<String, KbModule> IncludeList(IncludeProvider ip) throws ParseException {Map<String, KbModule> ret = new LinkedHashMap<String, KbModule>();
  Map<String, KbModule> added = null;
  String includeLine = null;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INCLUDE_LITERAL:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      added = Include(ip);
ret.putAll(added);
    }
{if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

/**
 * Method parses one include line.
 */
  final public Map<String, KbModule> Include(IncludeProvider ip) throws ParseException {Token pathToken;
    pathToken = jj_consume_token(INCLUDE_LITERAL);
try {
            {if ("" != null) return ip.parseInclude(pathToken.toString());}
        } catch (KidlParseException ex) {
                generateParseException(ex);
        }
    throw new Error("Missing return statement in function");
  }

/**
 * Method iterates over modules.
 */
  final public Map<String, KbModule> ModuleList(Map<String, KbModule> includes) throws ParseException {Map<String, KbModule> ret = new LinkedHashMap<String, KbModule>();
  KbModule module = null;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case T_module:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      module = Module(includes);
ret.put(module.getModuleName(), module);
      includes.put(module.getModuleName(), module);
    }
{if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public Token Identifier() throws ParseException {Token ret = null;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case S_IDENTIFIER:{
      ret = jj_consume_token(S_IDENTIFIER);
      break;
      }
    case T_required:{
      ret = jj_consume_token(T_required);
      break;
      }
    case T_optional:{
      ret = jj_consume_token(T_optional);
      break;
      }
    case T_none:{
      ret = jj_consume_token(T_none);
      break;
      }
    case T_async:{
      ret = jj_consume_token(T_async);
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

/**
 * Method parses one module. So it iterates over module components (typedefs, funcdefs and auths).
 * They are separated by semicolon.
 */
  final public KbModule Module(Map<String, KbModule> includes) throws ParseException {Token first = null;
  KbModule ret = null;
  String comment = null;
  Token srvToken = null;
  Token nameToken = null;
  KbModuleComp comp = null;
    first = jj_consume_token(T_module);
comment = getLastComment(first);
    if (jj_2_1(2147483647)) {
      srvToken = Identifier();
      jj_consume_token(T_colon);
    } else {
      ;
    }
    nameToken = Identifier();
ret = new KbModule(srvToken == null ? null : srvToken.toString(), nameToken.toString(), comment);
    jj_consume_token(T_figure_open_bracket);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case T_auth:
      case T_typedef:
      case T_funcdef:
      case T_async:{
        ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case T_typedef:{
        comp = Typedef(ret, includes);
        break;
        }
      case T_funcdef:
      case T_async:{
        comp = Funcdef(ret, includes);
        break;
        }
      case T_auth:{
        comp = Auth();
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(T_semicolon);
if (!(comp instanceof KbAuthdef))
          lastComment.set(null);
      ret.addModuleComponent(comp);
    }
    jj_consume_token(T_figure_close_bracket);
    jj_consume_token(T_semicolon);
{if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

/**
 * Method parses type definition (one of scalar, unspecified object, list, mapping, tuple, structure and 
 * reference to another typedef).
 */
  final public KbTypedef Typedef(KbModule curModule, Map<String, KbModule> includes) throws ParseException {Token first;
  String comment;
  KbType type;
  Token name;
    first = jj_consume_token(T_typedef);
comment = getLastComment(first);
    type = Type(curModule, includes);
    name = Identifier();
try {
                {if ("" != null) return new KbTypedef(curModule.getModuleName(), name.toString(), type, comment);}
        } catch (KidlParseException ex) {
                generateParseException(ex);
        }
    throw new Error("Missing return statement in function");
  }

/**
 * Method parses nameless type definition which can be used in typedefs, funcdefs and as part of 
 * another type. This type can be one of scalar, unspecified object, list, mapping, tuple, structure and 
 * reference to another typedef). Structure can't be type of funcdef parameter or part of another type. 
 */
  final public KbType Type(KbModule curModule, Map<String, KbModule> includes) throws ParseException {KbType ret = null;
  Token t = null;
  KbType subType;
  KbType subType2;
  KbParameter tupleElem;
  KbStructItem structItem;
  Token moduleToken = null;
  Token typeToken = null;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case T_string:{
      t = jj_consume_token(T_string);
ret = new KbScalar(t.toString());
      break;
      }
    case T_int:{
      t = jj_consume_token(T_int);
ret = new KbScalar(t.toString());
      break;
      }
    case T_float:{
      t = jj_consume_token(T_float);
ret = new KbScalar(t.toString());
      break;
      }
    case T_unobj:{
      t = jj_consume_token(T_unobj);
ret = new KbUnspecifiedObject();
      break;
      }
    case T_list:{
      t = jj_consume_token(T_list);
      jj_consume_token(39);
      subType = Type(curModule, includes);
      jj_consume_token(40);
ret = new KbList(subType);
      break;
      }
    case T_mapping:{
      t = jj_consume_token(T_mapping);
      jj_consume_token(39);
      subType = Type(curModule, includes);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case T_required:
      case T_optional:
      case T_none:
      case T_async:
      case S_IDENTIFIER:{
        Identifier();
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        ;
      }
      jj_consume_token(T_comma);
      subType2 = Type(curModule, includes);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case T_required:
      case T_optional:
      case T_none:
      case T_async:
      case S_IDENTIFIER:{
        Identifier();
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        ;
      }
      jj_consume_token(40);
ret = new KbMapping(subType, subType2);
      break;
      }
    case T_tuple:{
      t = jj_consume_token(T_tuple);
ret = new KbTuple();
      jj_consume_token(39);
      tupleElem = OptNameParam(curModule, includes);
((KbTuple)ret).addElement(tupleElem);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case T_comma:{
          ;
          break;
          }
        default:
          jj_la1[7] = jj_gen;
          break label_4;
        }
        jj_consume_token(T_comma);
        tupleElem = OptNameParam(curModule, includes);
((KbTuple)ret).addElement(tupleElem);
      }
      jj_consume_token(40);
      break;
      }
    case T_structure:{
      t = jj_consume_token(T_structure);
      jj_consume_token(T_figure_open_bracket);
ret = new KbStruct();
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case T_required:
        case T_optional:
        case T_none:
        case T_string:
        case T_int:
        case T_float:
        case T_unobj:
        case T_list:
        case T_mapping:
        case T_structure:
        case T_tuple:
        case T_async:
        case S_IDENTIFIER:{
          ;
          break;
          }
        default:
          jj_la1[8] = jj_gen;
          break label_5;
        }
        structItem = StructItem(curModule, includes);
        jj_consume_token(T_semicolon);
boolean ok = ((KbStruct)ret).addItem(structItem);
            if (!ok)
              generateParseException("Name duplication for field [" + structItem.getName() + "]");
      }
      jj_consume_token(T_figure_close_bracket);
      break;
      }
    case T_required:
    case T_optional:
    case T_none:
    case T_async:
    case S_IDENTIFIER:{
      if (jj_2_2(2147483647)) {
        moduleToken = Identifier();
        jj_consume_token(T_dot);
      } else {
        ;
      }
      typeToken = Identifier();
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
if (ret == null) {
        String module = moduleToken == null ? null : moduleToken.toString();
        KbModule refModule = null;
        if (module == null || module.equals(curModule.getModuleName())) {
                refModule = curModule;
        } else {
                refModule = includes.get(module);
                if (refModule == null)
                        generateParseException("Can not find module: " + module);
        }
        String type = typeToken.toString();
        ret = refModule.getNameToType().get(type);
        if (ret == null)
                generateParseException("Can not find type: " + (module == null ? "" : (module + ".")) + type);
    } else {
        ret.afterCreation();
    }
    {if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

/**
 * Element of structure. They are separated by semicolon.
 */
  final public KbStructItem StructItem(KbModule curModule, Map<String, KbModule> includes) throws ParseException {KbType type;
  Token name;
    type = Type(curModule, includes);
    name = Identifier();
{if ("" != null) return new KbStructItem(type, name.toString());}
    throw new Error("Missing return statement in function");
  }

  final public KbAuthdef Auth() throws ParseException {Token t;
    jj_consume_token(T_auth);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case T_required:{
      t = jj_consume_token(T_required);
      break;
      }
    case T_optional:{
      t = jj_consume_token(T_optional);
      break;
      }
    case T_none:{
      t = jj_consume_token(T_none);
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return new KbAuthdef(t.toString());}
    throw new Error("Missing return statement in function");
  }

/**
 * Method parses function definition.
 */
  final public KbFuncdef Funcdef(KbModule curModule, Map<String, KbModule> includes) throws ParseException {Token first;
  boolean async = false;
  KbFuncdef ret = null;
  String comment = null;
  Token name;
  List<KbParameter> args;
  List<KbParameter> rets;
  KbAuthdef auth;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case T_async:{
      jj_consume_token(T_async);
async = true;
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      ;
    }
    first = jj_consume_token(T_funcdef);
comment = getLastComment(first);
    name = Identifier();
    jj_consume_token(T_round_open_bracket);
ret = new KbFuncdef(name.toString(), comment, async);
    args = OptNameParams(curModule, includes);
ret.getParameters().addAll(args);
    jj_consume_token(T_round_close_bracket);
    jj_consume_token(T_returns);
    jj_consume_token(T_round_open_bracket);
    rets = OptNameParams(curModule, includes);
ret.getReturnType().addAll(rets);
    jj_consume_token(T_round_close_bracket);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case T_auth:{
      auth = Auth();
ret.setAuthentication(auth.getType());
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      ;
    }
{if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

/**
 * Method parses input or return parameters of function and elements of tuple.
 */
  final public List<KbParameter> OptNameParams(KbModule curModule, Map<String, KbModule> includes) throws ParseException {List<KbParameter> ret = new ArrayList<KbParameter>();
  KbParameter param;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case T_required:
    case T_optional:
    case T_none:
    case T_string:
    case T_int:
    case T_float:
    case T_unobj:
    case T_list:
    case T_mapping:
    case T_structure:
    case T_tuple:
    case T_async:
    case S_IDENTIFIER:{
      param = OptNameParam(curModule, includes);
ret.add(param);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case T_comma:{
          ;
          break;
          }
        default:
          jj_la1[13] = jj_gen;
          break label_6;
        }
        jj_consume_token(T_comma);
        param = OptNameParam(curModule, includes);
ret.add(param);
      }
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      ;
    }
{if ("" != null) return ret;}
    throw new Error("Missing return statement in function");
  }

/**
 * Method parses one input or return parameter of function and one element of tuple.
 */
  final public KbParameter OptNameParam(KbModule curModule, Map<String, KbModule> includes) throws ParseException {KbType type;
  Token nameToken;
  String name = null;
    type = Type(curModule, includes);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case T_required:
    case T_optional:
    case T_none:
    case T_async:
    case S_IDENTIFIER:{
      nameToken = Identifier();
name = nameToken.toString();
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      ;
    }
{if ("" != null) return new KbParameter(type, name);}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3_1()
 {
    if (jj_3R_7()) return true;
    if (jj_scan_token(T_colon)) return true;
    return false;
  }

  private boolean jj_3R_7()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(34)) {
    jj_scanpos = xsp;
    if (jj_scan_token(11)) {
    jj_scanpos = xsp;
    if (jj_scan_token(12)) {
    jj_scanpos = xsp;
    if (jj_scan_token(13)) {
    jj_scanpos = xsp;
    if (jj_scan_token(25)) return true;
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3_2()
 {
    if (jj_3R_7()) return true;
    if (jj_scan_token(T_dot)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public SpecParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x0,0x200,0x2003800,0x200c400,0x200c400,0x2003800,0x2003800,0x10000000,0x2ff3800,0x2ff3800,0x3800,0x2000000,0x400,0x10000000,0x2ff3800,0x2003800,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x40,0x0,0x4,0x0,0x0,0x4,0x4,0x0,0x4,0x4,0x0,0x0,0x0,0x0,0x4,0x4,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public SpecParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SpecParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SpecParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public SpecParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new SpecParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public SpecParser(SpecParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(SpecParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[41];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 41; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
