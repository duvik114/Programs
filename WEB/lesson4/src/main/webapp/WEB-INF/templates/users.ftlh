<#import "./common.ftlh" as c>

<#assign styleList = ["/css/style.css", "/css/users.css"]/>

<@c.page styleList>
    <div class="datatable">
        <div class="caption">Submissions</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>User</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td><@c.userlink u/></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
