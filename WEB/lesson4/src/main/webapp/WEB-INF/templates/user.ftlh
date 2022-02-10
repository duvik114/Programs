<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="long" -->
<#import "./common.ftlh" as c>

<#assign styleList = ["/css/style.css"]/>

<@c.page styleList>
    <#if user_id?has_content && user_id?is_number>
        <#assign u = c.findBy(users, "id", user_id)!/>
    <#elseif handle?has_content>
        <#assign u = c.findBy(users, "handle", handle)!/>
    </#if>
    <div class="users-pages">
        <#if u?has_content>
            <div class="users-pages-element">
                <@c.userlink u/>
                <img src="img/userImage.png" alt=${u.name}>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                </ul>
            </div>
            <div class="steps">
                <#if c.prevUser(u)??>
                    <a href="user?handle=${c.prevUser(u).handle}" class="full_href">←</a>
                <#else>
                    <a href="" class="empty_href">←</a>
                </#if>

                <#if c.nextUser(u)??>
                    <a href="user?handle=${c.nextUser(u).handle}" class="full_href">→</a>
                <#else>
                    <a href="" class="empty_href">→</a>
                </#if>
            </div>
        <#else>
            <div class="error" style="font-size: 1.8rem; font-weight: bold">404</div>
            <div class="message" style="margin: 1rem 0; font-size: 0.75rem">No such user</div>
        </#if>
    </div>
</@c.page>
