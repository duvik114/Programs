<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">Article</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>HideOrShow</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td class="article-id">${article.id}</td>
                    <td class="article_title">${article.title}</td>
                    <td class="article_hidden">
                        <div class="button-field">
                            <#if article.hidden == true>
                                <input id="${article.id}" type="button" value="Show">
                            <#else>
                                <input id="${article.id}" type="button" value="Hide">
                            </#if>
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".articles .article_hidden input").click(function () {
                const $input = $(this);
                const data = {
                    action: "hideOrShow",
                    id: $input.attr('id'),
                    value: $input.attr('value')
                }
                const success = function (response) {
                    $input.attr('value', response['hidden']);
                }

                ajaxFunction(data, success);

                return false;
            });
        })
    </script>
</@c.page>
