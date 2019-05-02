<template>
    <!-- [레] 회원검색 : s -->
    <div class="popupWrap popFind">
        <div class="popupContent">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">벤더검색</strong>
                </div>
                <div class="popupCont">
                    <!-- 내용 : s -->
                    <div class="topTitle">
                        <div class="titleBox">
                            <label class="mr10">벤더검색</label>
                            <input id="searchInput" type="text" class="inpText mr10" style="width:200px" v-model="search" @keydown.enter="searchVender()">
                            <a class="btns btnBlack" @click="searchVender()"><span>검색</span></a>
                        </div>
                    </div>
                    <div class="tblData">
                        <table>
                            <caption>회원검색</caption>
                            <colgroup>
                                <col style="width:90px">
                                <col style="width:auto">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col"><span class="require">선택</span></th>
                                    <th scope="col">벤더이름</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="vender in venders" :key="vender.id" @click="selectVender(vender)"> 
                                    <td>
                                        <div class="radioWrap solo">
                                            <span>
                                                <input type="radio" class="inpRadio" name="rad009" :id="vender.id"> 
                                                <label :for="vender.id">
                                                    <span class="blind">해당항목 선택</span>
                                                </label>
                                            </span>
                                        </div>
                                    </td>
                                    <td class="textOverflow" :title="vender.venderName">{{vender.venderName}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="btnArea right btm">
                        <a class="btns btnBlack" @click="save()">
                            <span>저장</span>
                        </a>
                        <a @click="$emit('close')" class="btns btnWhite">
                            <span>닫기</span>
                        </a>
                    </div>
                    <!-- 내용 : e -->
                </div>
            </div>
        </div>
    </div>
<!-- [레] 회원검색 : e -->
</template>

<script>
export default {
    name: 'vender-search-modal',

    props: ['vender'],

    data() {
        return {
            search: '',
            venders: [],
            tmpVender: {}
        }
    },

    methods: {
        searchVender: function() {
            if (this.search.length > 0) {
                axios.get('/admin/vender', {
                    "params": {
                        search: this.search
                    }
                }).then((response) => {
                    this.venders = response.data;
                }).catch((error) => {
                    console.log(error);
                });
            }
        },
        
        selectVender: function (vender) {
            this.tmpVender = vender;
        },

        save: function() {
            this.vender.venderId = this.tmpVender.venderId;
            this.vender.venderName = this.tmpVender.venderName;
            this.vender.spidId = this.tmpVender.spidId;
            this.$emit('close');
        },
    },
}
</script>

<style scoped>

</style>