<#import "./common.ftlh" as c>
<#-- @ftlvariable name="post_id" type="long" -->

<#assign styleList = ["/css/style.css", "/css/postStyle.css"]/>

<@c.page styleList>
    <#assign p = c.findBy(posts, "id", post_id)/>
    <@c.post p true/>
</@c.page>
