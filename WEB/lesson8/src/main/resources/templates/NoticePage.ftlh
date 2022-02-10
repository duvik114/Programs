<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice">
        <form method="post" action="">
            <input type="hidden" name="action" value="createNotice">
            <div class="content">
                <div class="content">
                    <label for="content">Content</label>
                </div>
                <label>
                    <textarea name="content" style="resize: none; width:44em; height:16em;"></textarea>
                </label>
                <@c.error "noticeForm.content"/>
            </div>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>
        </form>
    </div>
</@c.page>
