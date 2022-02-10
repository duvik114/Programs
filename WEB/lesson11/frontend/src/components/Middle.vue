<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <UserTable v-if="page === 'UserTable'"/>
            <Index v-if="page === 'Index'" :posts="viewFullPosts"/>
            <Register v-if="page === 'Register'"/>
            <Enter v-if="page === 'Enter'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import UserTable from "./main/UserTable";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index" //
        }
    },
    components: {
        UserTable,
        Register,
        Enter,
        Index,
        Sidebar
    },
    props: ["posts", "users"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        viewFullPosts: function () {
          return Object.values(this.posts).sort((a, b) => b.id - a.id);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    }
}
</script>

<style scoped>

</style>
