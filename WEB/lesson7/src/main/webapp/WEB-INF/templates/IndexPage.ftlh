<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <template>
            <article>
                <div class="title" style="margin-top: 1em"></div>
                <div class="information"></div>
                <div class="body"></div>
                <ul class="attachment">
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                </ul>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        2 days ago
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
        <div class="noData">
            <p>No data</p>
        </div>
    </div>

    <script>
        $(function () {
            const $noData = $(".noData");
            $noData.find("p").text("Loading...");

            const data = {
                action: "findAll"
            }

            const success = function (response) {
                if (response["articleCount"] !== 0) {
                    response["articles"].forEach(function (article) {
                        $noData.hide();
                        if (!article["hidden"]) {
                            const $article = $($(".articles template").prop("content")).clone();
                            $article.find(".title").text(article["title"]);
                            $article.find(".information").text("By " + response["userLoginMap"][article["userId"]]
                                + ", " + article["creationTime"]);
                            $article.find(".body").html(article["text"].replaceAll("\n", "<br>"));
                            $article.find("article").attr("data-articleId", article["id"]);
                            $(".articles").append($article);
                        }
                    });
                    $(".articles article").click(function () {
                        const data = {
                            action: "findArticle",
                            articleId: $(this).attr("data-articleId")
                        }
                        const success = function (response) {
                            notify(response["article"].creationTime);
                        }

                        ajaxFunction(data, success)
                    });
                } else {
                    $noData.text("No Articles Found")
                }
            }

            ajaxFunction(data, success)
        })
    </script>
</@c.page>
