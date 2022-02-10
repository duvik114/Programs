<#import "./common.ftlh" as c>
<#-- @ftlvariable name="user_id" type="long" -->

<#assign styleList = ["/css/style.css", "/css/postStyle.css"]/>

<@c.page styleList>
    <ul>
        <#list posts?reverse as p>
            <#if p.user_id == user_id>
                <li>
                    <@c.post p false/>
                </li>
            </#if>
        </#list>
    </ul>
</@c.page>
