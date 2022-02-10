<#import "commons.ftlh" as c>

<@c.page>
    <div class="article">
        <form method="post">
            <input type="hidden" name="action" value="createArticle">
            <div class="field">
                <div class="name">
                    <label for="title">Title</label>
                </div>
                <div class="value">
                    <input id="title" name="title"/>
                </div>
            </div>
            <div class="text">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <label>
                    <textarea name="text" style="resize: none; width:44em; height:16em;"></textarea>
                </label>
            </div>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>
        </form>
    </div>

    <script>
        $(function () {
            $(".article form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const data = {
                    action: "createArticle",
                    title,
                    text
                }
                const success = defaultSuccess();

                ajaxFunction(data, success);

                return false;
            });
        })
    </script>
</@c.page>