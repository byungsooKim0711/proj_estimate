<template>
    <div id="header">
        <div id="gnb">
            <li><router-link to="/solution1">솔루션 1팀</router-link></li>
            <li><router-link to="/solution2">솔루션 2팀</router-link></li>
            <li><router-link to="/admin/sender">발신자 관리</router-link></li>
            <li><router-link to="/admin/vender">벤더 관리</router-link></li>
            <li><router-link to="/admin/tool">도구 관리</router-link></li>
            <li><router-link to="/admin/price">가격 관리</router-link></li>
            <li><router-link to="/">견적 관리</router-link></li>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Header',
    
    data () {
        return {
            teams: ['솔루션 1팀', '솔루션 2팀', '기술/영업 지원팀']
        }
    }
}
</script>

<style scoped>

</style>

