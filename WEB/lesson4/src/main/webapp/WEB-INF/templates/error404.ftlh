<#import "./common.ftlh" as c>

<#assign styleList = ["/css/style.css"]/>

<@c.page styleList>
    <div class="error" style="font-size: 1.8rem; font-weight: bold">404</div>
    <div class="message" style="margin: 1rem 0; font-size: 0.75rem">No such page</div>
</@c.page>