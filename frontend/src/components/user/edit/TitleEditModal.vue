<template>
    <div>
        <table class="titleedit">
            <thead>
                <tr>
                    <th scope="cols">제목</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <input placeholder="제목을 입력하세요." v-model="tmpEstimate.title" @keydown.enter="save"/>
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
    name: 'title-edit-modal',

    props: ['estimate'],

    data() {
        return {
            tmpEstimate: {...this.estimate}
        }
    },

    methods: {
        save: function() {
            this.estimate.title = this.tmpEstimate.title;
            this.$emit('close');
        },
    }

}
</script>

<style scoped>
.titleedit {
    position: relative;
    left: 20px;
    margin: 5px 5px 20px 5px;
}
th {
    font-size: 30px;
}
input {
    width: 700px;
}
</style>