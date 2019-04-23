<template>
    <div>
        <table class="recipientedit">
            <thead>
                <tr>
                    <th scope="cols">업체명</th>
                    <th scope="cols">담당자 이름</th>
                    <th scope="cols">전화번호</th>
                    <th scope="cols">이메일</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <input placeholder="회사명을 입력하세요." v-model="tmpEstimate.company"/>
                    </td>
                    <td>
                        <input placeholder="담당자를 입력하세요." v-model="tmpEstimate.incharge"/>
                    </td>
                    <td>
                        <input placeholder="전화번호를 입력하세요." v-model="tmpEstimate.tel"/>
                    </td>
                    <td>
                        <input placeholder="이메일을 입력하세요." v-model="tmpEstimate.email"/>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="btnArea right btm">
            <a @click="save()" class="btns btnBlack">
                <span>저장</span>
            </a>
            <a @click="$emit('close')" class="btns btnWhite">
                <span>닫기</span>
            </a>
        </div>
    </div>
</template>

<script>
export default {
    name: 'recipient-edit-modal',

    props: ['estimate'],

    data() {
        return {
            tmpEstimate: {...this.estimate}
        }
    },

    created() {

    },

    methods: {

        /* 수신자 정보 저장 */
        save: function() {
            this.estimate.company = this.tmpEstimate.company;
            this.estimate.incharge = this.tmpEstimate.incharge;
            this.estimate.tel = this.tmpEstimate.tel;
            this.estimate.email = this.tmpEstimate.email;
            this.$emit('close');
        },
    }

}
</script>

<style scoped>
.recipientedit {
    position: relative;
    margin: 5px 5px 20px 5px;
}
th {
    font-size: 20px;
    text-align: center;
}
</style>