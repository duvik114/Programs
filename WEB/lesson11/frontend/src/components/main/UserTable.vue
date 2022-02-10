<template>
  <div class="users datatable">
    <div class="caption">User</div>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody v-for="user in users" :key="user.id">
      <tr>
        <td class="id">{{ user.id }}</td>
        <td class="login"> {{ user.login }}</td>
        <td class="names"> {{ user.name }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserTable",
  /*props: ["users"]*/
  data: function () {
    return {
      users: []
    }
  },
  beforeMount() {
    axios.get("/api/1/users").then(response => {
      this.users = response.data;
    });
  }
}
</script>

<style scoped>
</style>