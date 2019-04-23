<template>
    <div class="container">
		<div id="content">
			<div class="">
                <a @click="showEstimate()">견적내기</a>
			</div>
		</div>
    </div>
</template>

<script>
import Estimatemodal from './EstimateModal.vue';

export default {
    name: 'estimate-main',

    methods: {
        showEstimate: function () {
            this.$modal.show(
                Estimatemodal, {
                    text: "Estimatemodal"
                }, 
                {
			        width: "794px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'show-estimate',
					clickToClose: false,
					transition:true
			    }
		    );
        }
    }
}
</script>

<style scoped>

</style>