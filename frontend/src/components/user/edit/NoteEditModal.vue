<template>
    <div>
        <table class="noteedit">
            <thead>
                <tr>
                    <th scope="cols">비고</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <textarea placeholder="비고 내용을 입력하세요." 
                                  v-model="tmpEstimate.estimateNote" @keydown.enter.exact.prevent
                                  @keyup.enter.exact="send"
                                  @keydown.enter.shift.exact="newline"/>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="btnArea right btm" data-html2canvas-ignore="true">
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
    name: 'note-edit-modal',

    props: ['estimate'],

    data() {
        return {
            tmpEstimate: {...this.estimate}
        }
    },

    methods: {
        save: function() {
            this.estimate.estimateNote = this.tmpEstimate.estimateNote;
            this.$emit('close');
        },

        send() {
            this.tmpEstimate.estimateNote += "<br>"
        }
    }

}
</script>

<style scoped>
.noteedit {
    position: relative;
    left: 20px;
    margin: 5px 5px 20px 5px;
}
th {
    font-size: 30px;
}
textarea {
    width: 700px;
    height: 400px;
}
</style>