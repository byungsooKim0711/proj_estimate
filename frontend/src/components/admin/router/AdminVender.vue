<template>
	<!-- container : s -->
	<div class="container">
		<div id="content">
			<div class="">
				<div class="serviceWrap">
					<div class="serviceCont side">
						<div class="">
							<h2 class="tit">벤더사 관리</h2>
							<p class="countMsg">총 <em class="count txtBlue">{{venders.length}}</em> 건의 벤더가 검색되었습니다.</p>
							<div class="btnArea right">
                                <input id="searchInput" v-model="search" @keyup="handleChange()" placeholder="검색" type="text" class="inpText mr10" style="width:200px;">
                                <span>검색</span>
								<a class="btns btnLineGray txtBlue" @click="insertVender()">
									<span>등록</span>
								</a>
							</div>
							<!-- tblData : s -->
							<div class="tblData list">
								<table>
									<colgroup>
										<!-- <col style="width:80px"> -->
										<col style="width:auto">
										<col style="width:auto">
										<col style="width:150px">
									</colgroup>
									<thead>
										<tr>
											<!-- <th scope="col">번호</th> -->
											<th scope="col">벤더사 이름</th>
											<th scope="col">벤더사 담당자 이름</th>
                                            <th scope="col">작업</th>
										</tr>
									</thead>
									<tbody>
										<tr v-for="vender in venders" :key="vender.id">
											<!-- <td>{{vender.venderId}}</td> -->
											<td>{{vender.venderName}}</td>   
                                            <td>{{vender.spidName}}({{vender.spidEmail}})</td>
                                            <td>
                                                <em @click="updateVender(vender)" class="btn txtGreen02">수정</em>
                                                <em @click="deleteVender(vender)" class="btn txtRed">삭제</em>
                                            </td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- tblData : e -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';
import VenderInsertModal from '../edit/vender/VenderInsertModal.vue';
import VenderUpdatemodal from '../edit/vender/VenderUpdateModal.vue';

export default {
    name: 'admin-vender',

	computed: {
		...mapGetters({
			venders: 'getVenders'
		})
	},

    data() {
        return {
            search: '',
            _timer: 5000,
        }
    },

    methods: {
        getVenders: function () {
            this.$store.dispatch('LOAD_VENDERS', this.search);
		},
		
		insertVender: function () {
			this.$modal.show(
                VenderInsertModal, {
                    text: 'text'
                }, 
                {
			        width: "600px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'VenderInsertModal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		updateVender: function (vender) {
			this.$modal.show(
                VenderUpdatemodal, {
                    vender: vender
                }, 
                {
			        width: "600px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'VenderUpdatemodal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		deleteVender: function (vender) {
			if (confirm("정말 삭제하시겠습니까?")) {
				this.$store.dispatch('DELETE_VENDER', vender);
			}
		},

		handleChange: function () {
			clearTimeout(this._timer);
			this._timer = window.setTimeout(() => {
				this.getVenders();
			}, 500);
		},
    },

    mounted() {
        this.getVenders();
    }
    
}
</script>

<style scoped>

</style>