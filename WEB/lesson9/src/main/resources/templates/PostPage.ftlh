<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->

<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=post/>
    <div class="form" style="margin: 0">
        <div class="header">Write Comment</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="writeComment"/>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text">${comment.text!}</textarea>
                    </div>
                    <@c.error "comment.text"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </div>

    <#list post.comments as comment>
        <@c.comment comment=comment/>
    </#list>
</@c.page>
