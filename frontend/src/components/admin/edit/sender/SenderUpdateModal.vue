<template>
    <div class="popupWrap popEnroll">
        <div class="popupContent small">
            <div class="popup">
                <div class="popupHead">
                    <strong class="tit">담당자 수정</strong>
                </div>
                <div class="popupCont">
                    <!-- 내용 : s -->
                    <div class="tblForm">
                        <table>
                            <caption></caption>
                            <colgroup>
                                <col style="width:117px">
                                <col style="width:auto">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>부서</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidDept" v-model="tmpSender.spidDept">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>이름</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidName" v-model="tmpSender.spidName">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>전화1</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidTel1" v-model="tmpSender.spidTel1">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>전화2</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidTel2" v-model="tmpSender.spidTel2">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>이메일</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="spidEmail" v-model="tmpSender.spidEmail">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th scope="row" class="vt">
                                        <span>직책</span>
                                    </th>
                                    <td>
                                        <div class="formHorizental">
                                            <span>
                                                <input type="text" class="inpText" name="job" v-model="tmpSender.job">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="btnArea right btm">
                        <a @click="updateSender()" class="btns btnBlack">
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
</template>

<script>
export default {
    name: 'sender-update-modal',

    props: ['sender'],

    data() {
        return {
            tmpSender: {}
        }
    },

    created() {
        this.tmpSender = {... this.sender};
    },

    methods: {
        updateSender: function () {
            this.$store.dispatch('UPDATE_SENDER', this.tmpSender);
            this.$emit('close');
        }
    },

}
</script>

<style scoped>

</style>