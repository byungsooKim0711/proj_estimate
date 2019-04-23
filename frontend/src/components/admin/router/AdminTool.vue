<template>
	<!-- container : s -->
	<div class="container">
		<div id="content">
			<div class="">
				<div class="serviceWrap">
					<div class="serviceCont side">
						<div class="">
							<h2 class="tit">제품군 관리</h2>
							<p class="countMsg">총 <em class="count txtBlue">{{conditionalTools.length}}</em> 건의 벤더가 검색되었습니다.</p>
							<div class="btnArea right">
								<span class="selectboxWrap mr10" style="width:130px">
									<select v-model="selectedVenderName">
										<option v-for="name in venderNames" :key="name">{{name}}</option>
									</select>
								</span>
                                <input id="searchInput" v-model="search" @keyup="handleChange()" placeholder="검색" type="text" class="inpText mr10" style="width:200px;">
                                <span>검색</span>
								<a class="btns btnLineGray txtBlue" @click="insertTool()">
									<span>등록</span>
								</a>
							</div>
							<!-- tblData : s -->
							<div class="tblData list">
								<table>
									<colgroup>
										<!-- <col style="width:35px"> -->
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
											<th scope="col">도구 이름</th>
											<th scope="col">모델번호</th>
                                            <th scope="col">라이센스</th>
                                            <th scope="col">상세정보</th>
                                            <th scope="col">벤더이름</th>
                                            <th scope="col">작업</th>
										</tr>
									</thead>
									<tbody>
										<tr v-for="tool in conditionalTools" :key="tool.toolId">
											<!-- <td>{{tool.toolId}}</td> -->
											<td>{{tool.toolName}}</td>   
                                            <td>{{tool.toolModelNumber}}</td>
                                            <td>{{tool.toolLicense}}</td>
                                            <td>{{tool.toolDetails}}</td>
                                            <td>{{tool.venderName}}</td>
                                            <td>
                                                <em @click="updateTool(tool)" class="btn txtGreen02">수정</em>
                                                <em @click="deleteTool(tool)" class="btn txtRed">삭제</em>
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
import ToolInsertModal from '../edit/tool/ToolInsertModal.vue';
import ToolUpdateModal from '../edit/tool/ToolUpdateModal.vue'

export default {
    name: 'admin-tool',

    data() {
        return {
            search: '',
            _timer: 5000,

			selectedVenderName: '전체',

			conditionalTools: []
        }
    },

    computed: {
        ...mapGetters({
			tools: 'getTools',
			venderNames: 'getVenderNames'
        })
	},
	
	watch: {
		selectedVenderName: function () {
			this.refresh();
		},

		tools: function () {
			this.refresh();
		}
	},

    methods: {
        getTools: function () {
			this.$store.dispatch('LOAD_TOOLS', this.search);
        },

		insertTool: function() {
			this.$modal.show(
                ToolInsertModal, {
                    text: 'text'
                }, 
                {
			        width: "600px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'ToolInsertModal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		updateTool: function(tool) {
			this.$modal.show(
                ToolUpdateModal, {
                    tool: tool
                }, 
                {
			        width: "600px",
					height: "auto",
					scrollable: true
                }, 
                {
			        name: 'ToolUpdateModal',
					clickToClose: false,
					transition:true
			    }
		    );
		},

		deleteTool: function(tool) {
			if (confirm(tool.toolName + " 도구를 삭제하시겠습니까?")) {
				this.$store.dispatch('DELETE_TOOL', tool);
			}
		},

		handleChange: function () {
			clearTimeout(this._timer);
			this._timer = window.setTimeout(() => {
				this.getTools();
			}, 500);
		},

		refresh: function () {
			if (this.selectedVenderName === '전체') {
				this.conditionalTools = this.tools;
			} else {
				this.conditionalTools = this.tools.filter(t => t.venderName === this.selectedVenderName);
			}
		}
    },

    mounted() {
        this.getTools();
    },
}
</script>

<style scoped>

</style>