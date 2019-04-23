<template>
	<!-- container : s -->
	<div class="container">
		<div id="content">
			<div class="">
				<div class="serviceWrap">
					<div class="serviceCont side">
						<div class="">
							<h2 class="tit">담당자 정보 관리</h2>
							<p class="countMsg">총 <em class="count txtBlue">{{senders.length}}</em> 건의 담당자가 검색되었습니다.</p>
							<div class="btnArea right">
                                <input id="searchInput" v-model="search" @keyup="handleChange()" placeholder="검색" type="text" class="inpText mr10" style="width:200px;">
                                <span>검색</span>
								<a class="btns btnLineGray txtBlue" @click="insertSender()">
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
										<col style="width:auto">
										<col style="width:auto">
										<col style="width:auto">
										<col style="width:auto">
										<col style="width:150px">
									</colgroup>
									<thead>
										<tr>
											<!-- <th scope="col">번호</th> -->
											<th scope="col">부서</th>
											<th scope="col">이름</th>
                                            <th scope="col">전화1</th>
                                            <th scope="col">전화2</th>
                                            <th scope="col">이메일</th>
                                            <th scope="col">직책</th>
                                            <th scope="col">작업</th>
										</tr>
									</thead>
									<tbody>
										<tr v-for="sender in senders" :key="sender.id">
											<!-- <td>{{sender.spidId}}</td> -->
                                            <td>{{sender.spidDept}}</td>
                                            <td>{{sender.spidName}}</td>
                                            <td>{{sender.spidTel1}}</td>
                                            <td>{{sender.spidTel2}}</td>
											<td>{{sender.spidEmail}}</td>
                                            <td>{{sender.job}}</td>    
                                            <td>
                                                <em @click="updateSender(sender)" class="btn txtGreen02">수정</em>
                                                <em @click="deleteSender(sender)" class="btn txtRed">삭제</em>
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
import SenderInsertModal from '../edit/sender/SenderInsertModal.vue';
import SenderUpdateModal from '../edit/sender/SenderUpdateModal.vue';

export default {
    name: 'admin-sender',

	computed: {
		...mapGetters({
			senders: 'getSenders'
		})
	},

    data() {
        return {
            search: '',
            _timer: 5000,
        }
    },

    methods: {
        getSenders: function () {
            this.$store.dispatch('LOAD_SENDERS', this.search);
		},
		
		insertSender: function () {
			this.$modal.show(
                SenderInsertModal, {
                    text: 'text'
                }, 
                {
			        width: "400px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'SenderInsertModal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		updateSender: function (sender) {
			this.$modal.show(
                SenderUpdateModal, {
                    sender: sender
                }, 
                {
			        width: "400px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'SenderUpdateModal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		deleteSender: function (sender) {
			if (confirm("정말 삭제하시겠습니까?")) {
				this.$store.dispatch('DELETE_SENDER', sender);
			}
		},

		handleChange: function () {
			clearTimeout(this._timer);
			this._timer = window.setTimeout(() => {
				this.getSenders();
			}, 500);
		}
    },

    mounted() {
        this.getSenders();
    }
    
}
</script>

<style scoped>

</style>