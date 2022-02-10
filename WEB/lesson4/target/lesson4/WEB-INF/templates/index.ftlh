<#import "./common.ftlh" as c>

<#assign styleList = ["/css/style.css", "/css/postStyle.css"]/>

<@c.page styleList>
    <ul>
        <#list posts?reverse as p>
            <li>
                <@c.post p false/>
            </li>
        </#list>
    </ul>
</@c.page>
