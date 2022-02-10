<#import "commons.ftlh" as c>

<@c.page>
    <div class="user">
        <#if user??>
            <p class="id">id : ${user.id}</p>
            <p class="login">login : ${user.login}</p>
            <p class="creationTime">creationTime : ${user.creationTime}</p>
        <#else>
            <p>No such user</p>
        </#if>
    </div>
</@c.page>
