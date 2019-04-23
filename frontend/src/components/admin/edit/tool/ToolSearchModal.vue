<template>
	<!-- [레] 회원검색 : s -->
	<div class="popupWrap popFind">
		<div class="popupContent">
			<div class="popup">
				<div class="popupHead">
					<strong class="tit">도구검색</strong>
				</div>
				<div class="popupCont">
					<!-- 내용 : s -->
					<div class="topTitle">
						<div class="titleBox">
							<label class="mr10">도구검색</label>
							<input id="searchInput" type="text" class="inpText mr10" style="width:200px" v-model="search" @keydown.enter="searchTool">
							<a class="btns btnBlack" @click="searchTool"><span>검색</span></a>
						</div>
					</div>
					<div class="tblData">
						<table>
							<caption>도구검색</caption>
							<colgroup>
								<col style="width:90px">
								<col style="width:auto">
							</colgroup>
							<thead>
								<tr>
									<th scope="col"><span class="require">선택</span></th>
									<th scope="col">도구</th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="tool in tools" :key="tool.id" @click="selectTool(tool)"> 
									<td>
										<div class="radioWrap solo">
											<span>
												<input type="radio" class="inpRadio" name="rad009" :id="tool.id"> 
												<label :for="tool.id">
													<span class="blind">해당항목 선택</span>
												</label>
											</span>
										</div>
									</td>
									<td class="textOverflow" :title="tool.toolName">{{tool.toolName}}({{tool.toolLicense}})</td>
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
    name: 'tool-search-modal',

    props: ['tool'],

    data() {
        return {
            search: '',
            tools: [],
            tmpTool: {}
        }
    },

    methods: {
        searchTool: function() {
            if (this.search.length > 0) {
                axios.get('/admin/tool', {
                    "params": {
                        search: this.search
                    },
                    "headers": {
                        'Content-Type': 'application/JSON; charset=UTF-8'
                    }
                }).then((response) => {
                    this.tools = response.data;
                }).catch((error) => {
                    console.log(error);
                });
            }
        },

        selectTool: function(tool) {
            this.tmpTool = tool;
        },

        save: function() {
			this.tool.toolId = this.tmpTool.toolId;
			this.tool.toolName = this.tmpTool.toolName;
			this.tool.toolModelNumber = this.tmpTool.toolModelNumber;
			this.tool.toolLicense = this.tmpTool.toolLicense;
			this.tool.toolDetails = this.tmpTool.toolDetails;
			this.tool.venderId = this.tmpTool.venderId;
            this.$emit('close');
        }

    },

    
}
</script>

<style scoped>

</style>