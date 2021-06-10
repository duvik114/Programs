
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class Scan {
    private final Reader in;
    private StringBuilder strBuild = null;
    private int markPosition = 0;
    private StringBuilder word = new StringBuilder();
    private Integer number = null;

    public String nextLine() {
        if(strBuild != null) {
            String s = strBuild.toString();
            strBuild = null;
            return s;
        } else {
            if(hasnextLine()) {
                String s = strBuild.toString();
                strBuild = null;
                return s;
            } else { //нахрена????
                return null;
            }
        }
    }
    public boolean hasnextLine() {
        try {
            if(strBuild != null) {
                return true;
            } else {
                int i = in.read();
                if(i != -1) {
                    strBuild = new StringBuilder();
                    while((i != -1)&&((char) i != '\n')) { ///
                        strBuild.append((char) i);
                        i = in.read();
                    }
                    return true;//красава
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.print("I/O error: " + e.getMessage());
            return false;
        }
    }
    public boolean hasNext() {
        if(word.length() != 0) {
            return true;
        } else {
                boolean stopper = false;
                while (hasnextLine()) {
                    while(markPosition != strBuild.length()) {
                        if(Character.isWhitespace(strBuild.charAt(markPosition))) {
                            if(word.length() == 0) {
                                markPosition++;
                            } else {
                                markPosition++;
                                stopper = true;
                                break;
                            }
                        } else {
                            word.append(strBuild.charAt(markPosition));
                            markPosition++;
                        }
                    }
                    if(markPosition == strBuild.length()) {
                        markPosition = 0;
                        strBuild = null;
                        if(word.length() != 0) {
                            stopper = true;
                        }
                    }
                    if(stopper){break;}
                }
            return word.length() != 0;
        }
    }
    public  String next() {
        if(hasNext()) {
            String s = word.toString();
            word = new StringBuilder();
            number = null;
            return  s;
        } else {
            return null;
        }
    }
    public boolean hasNextInt() {
        if(number != null) {
            return true;
        } else {
            while (hasNext()) {
                try {
                    number = Integer.parseInt(word.toString());
                } catch (Exception e) {
                    word = new StringBuilder();
                    number = null;
                }
                if(number != null) {
                    break;
                }
            }
            return number != null;
        }
    }
    public Integer nextInt() {
        if(hasNextInt()) {
            word = new StringBuilder();
            Integer n = number;
            number = null;
            return n;
        } else {
            return null;
        }
    }
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.out.println("I/O error " + e.getMessage());
        }

    }
    public Scan(Reader in) {
        this.in = in;
        if(!this.hasnextLine()) {
            strBuild = new StringBuilder(" ");
        }
    }
    public Scan(InputStream i) {
        this.in = new InputStreamReader(i);
        if(!this.hasnextLine()) {
            strBuild = new StringBuilder(" ");
        }
    }
    public Scan(String in) {
        this.in = new StringReader(in);
        if(!this.hasnextLine()) {
            strBuild = new StringBuilder(" ");
        }
    }
    public Scan(Path p, Charset chS) throws FileNotFoundException {
            this.in = new InputStreamReader(new FileInputStream(p.toString()), chS);
            if(!this.hasnextLine()) {
                strBuild = new StringBuilder(" ");
        }
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          