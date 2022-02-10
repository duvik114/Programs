<#-- @ftlvariable name="postCredentials" type="ru.itmo.wp.form.PostCredentials" -->

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form">
        <div class="header">Write Post</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="writePost"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input autofocus id="title" name="title" value="${postCredentials.title!}"/>
                    </div>
                    <@c.error "postCredentials.title"/>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text">${postCredentials.text!}</textarea>
                    </div>
                    <@c.error "postCredentials.text"/>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="tags">Tags</label>
                    </div>
                    <div class="value">
                        <textarea id="tags" name="tags">${postCredentials.tags!}</textarea>
                    </div>
                    <@c.error "postCredentials.tags"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </div>
</@c.page>
