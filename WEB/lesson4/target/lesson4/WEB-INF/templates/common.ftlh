<#-- @ftlvariable name="uri" type="java.lang.String" -->
<#macro page styleList>
    <!DOCTYPE html>
    <html lang="ru">
    <@head styleList=styleList>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <#nested>
        <link rel="icon" href="/favicon.ico">
    </@head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro head styleList>
    <#list styleList as style>
        <link rel="stylesheet" href="${style}">
    </#list>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if uri?? && item.href == uri>
                            <a href="${item.href}" style="border-bottom: 4px solid #3B5998">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as p>
            <section>
                <div class="header">Post#${p.id}</div>
                <@short_post p false></@short_post>
                <div class="footer">
                    <a href="/post?post_id=${p.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro short_post p full>
    <div class="body">
        <#if full>
            <p class="text">${p.text}</p>
        <#elseif p.text?length &lt; 250>
            <p class="text">${p.text}</p>
        <#else>
            <p class="text">${p.text?substring(0, 250) + "..."}</p>
        </#if>
    </div>
</#macro>

<#macro post p full>
    <article class="post_text">
        <#assign author = findBy(users, "id", p.user_id)!/>
        <p class="title">${p.title}</p>
        <#if author?has_content>
            <div class="information">By ${author.name}</div>
        </#if>
        <@short_post p full/>
        <ul class="attachment">
            <li>Announcement of <a href="">Codeforces Round #777 (Div. 1)</a></li>
            <li>Announcement of <a href="">Codeforces Round #777 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="/img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="/img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="">68</a>
            </div>
        </div>
    </article>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly=false>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.handle}</a>
    <#else>
        <a href="/user?handle=${user.handle}" class="colored_user" style="color: ${user.color}">${user.handle}</a>
    </#if>
</#macro>

<#--<#macro userlink user>
    <a href="/user?handle=${user.handle}">${user.name}</a>
</#macro>-->

<#function prevUser curUser>
    <#list 0..users?size-1 as i>
        <#if users[i] == curUser && i != 0>
            <#return users[i - 1]/>
        </#if>
    </#list>
</#function>

<#function nextUser curUser>
    <#list 0..users?size-1 as i>
        <#if users[i].id == curUser.id && i != users?size-1>
            <#return users[i + 1]/>
        </#if>
    </#list>
</#function>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>
