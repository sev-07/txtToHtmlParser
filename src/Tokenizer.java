import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private class TokenInfo {
        public final Pattern regex;
        public final int token;

        public TokenInfo(Pattern regex, int token) {
            super();
            this.regex = regex;
            this.token = token;
        }
    }

    private LinkedList<TokenInfo> tokenInfos;

    public Tokenizer() {
        tokenInfos = new LinkedList<>();
        tokens = new LinkedList<>();
    }

    public void add(String regex, int token) {
        tokenInfos.add(
                new TokenInfo(
                        Pattern.compile("^(" + regex + ")"), token
                )
        );
    }

    public class Token {
        public final int token;
        public final String sequence;

        public Token(int token, String sequence) {
            super();
            this.token = token;
            this.sequence = sequence;
        }
    }

    private LinkedList<Token> tokens;

    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.add("T", 1);
        tokenizer.add("P", 2);
        tokenizer.add("#", 3);
        tokenizer.add("B", 4);
        tokenizer.add("I", 5);
        tokenizer.add(" ", 5);
        tokenizer.add("[a-zA-Z .]*", 6);
    }

    public void tokenize(String str) {
        String s = new String(str);
        tokens.clear();

        while (!s.equals("")) {
            boolean match = false;

            for (TokenInfo info : tokenInfos) {
                Matcher m = info.regex.matcher(s);
                if (m.find()) {
                    match = true;

                    String tok = m.group().trim();
                    tokens.add(new Token(info.token, tok));

                    s = m.replaceFirst("");
                    break;
                }
            }
        }

    }

}
