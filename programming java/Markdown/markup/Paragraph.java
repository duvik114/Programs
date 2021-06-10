package markup;

import java.util.List;

abstract class Razmetka {
    abstract void toMarkdown(StringBuilder s);
    abstract void toHtml(StringBuilder s);
    protected void ForMarkdown(StringBuilder justBuild, String justString, List<Razmetka> justList) {
        justBuild.append(justString);
        for (Razmetka razmetka : justList) {
            razmetka.toMarkdown(justBuild);
        }
        justBuild.append(justString);
    }
    protected void ForHtml(StringBuilder justBuild, String justString, List<Razmetka> justList) {
        justBuild.append("<").append(justString).append(">");
        for (Razmetka razmetka : justList) {
            razmetka.toHtml(justBuild);
        }
        justBuild.append("</").append(justString).append(">");
    }
    protected void ForHtml(StringBuilder justBuild, List<Razmetka> justList) {
        for (Razmetka razmetka : justList) {
            razmetka.toHtml(justBuild);
        }
    }
}

class Text extends Razmetka {

    protected StringBuilder textBuild = new StringBuilder();

    public Text(String s) {
        textBuild.append(s);
    }
    public void toMarkdown(StringBuilder strBuild) {
        strBuild.append(textBuild);
    }
    public void toHtml(StringBuilder strBuild) {
        strBuild.append(textBuild);
    }
}

class Emphasis extends Razmetka {

    private final List<Razmetka> l;

    public Emphasis(List<Razmetka> l) {
        this.l = l;
    }
    public void toMarkdown(StringBuilder strBuild) {
        ForMarkdown(strBuild, "*", l);
    }
    public void toHtml(StringBuilder strBuild) {
        ForHtml(strBuild, "em", l);
    }
}

class Strong extends Razmetka {

    private final List<Razmetka> l;

    public Strong(List<Razmetka> l) {
        this.l = l;
    }
    public void toMarkdown(StringBuilder strBuild) {
        ForMarkdown(strBuild, "__", l);
    }
    public void toHtml(StringBuilder strBuild) {
        ForHtml(strBuild, "strong", l);
    }
}

class Strikeout extends Razmetka {

    private final List<Razmetka> l;

    public Strikeout(List<Razmetka> l) {
        this.l = l;
    }
    public void toMarkdown(StringBuilder strBuild) {
        ForMarkdown(strBuild, "~", l);
    }
    public void toHtml(StringBuilder strBuild) {
        ForHtml(strBuild, "s", l);
    }
}

public class Paragraph extends Razmetka {

    private final List<Razmetka> l;

    public Paragraph(List<Razmetka> l) {
        this.l = l;
    }

    public void toMarkdown(StringBuilder strBuild) {
        ForMarkdown(strBuild, "", l);
    }
    public void toHtml(StringBuilder strBuild) {
        ForHtml(strBuild, l);
    }
}