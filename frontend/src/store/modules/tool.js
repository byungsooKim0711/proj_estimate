import Vue from 'vue';
const state = {
    tools: [],
    venderNames: ['전체'],
}

const getters = {
    getTools (state) {
        return state.tools;
    },

    getVenderNames (state) {
        state.tools.forEach(tool => {
            if (state.venderNames.findIndex(n => n === tool.venderName) === -1) {
                state.venderNames.push(tool.venderName);
            }
        });
        return state.venderNames;
    }
}

const mutations = {
    LOAD_TOOLS (state, tools) {
        state.tools = tools;
    },

    DELETE_TOOL (state, tool) {
        state.tools = state.tools.filter(t => t.toolId != tool.toolId);
    },

    UPDATE_TOOL (state, tool) {
        Vue.set(state.tools, state.tools.findIndex(t => t.toolId == tool.toolId), tool);
    },

    INSERT_TOOL (state, tool) {
        state.tools.push(tool);
    }
}

const actions = {
    LOAD_TOOLS ( {commit}, search ) {
        return axios.get('/admin/tool', {
            "params": {
                ...(search ? {search: search}: {})
            }
        }).then((response) => {
            commit('LOAD_TOOLS', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    DELETE_TOOL ( {commit}, tool ) {
        return axios.delete('/admin/tool/' + tool.toolId, {

        }).then((response) => {
            if (response.status === 202) {
                commit('DELETE_TOOL', tool);
            }
        }).catch((error) => {
            console.log(error);
        });
    },

    UPDATE_TOOL ( {commit}, tool) {
        return axios.put('/admin/tool/' + tool.toolId, tool, {

        }).then((response) => {
            commit('UPDATE_TOOL', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    INSERT_TOOL ( {commit}, tool) {
        return axios.post('/admin/tool', tool, {

        }).then((response) => {
            commit('INSERT_TOOL', response.data);
        }).catch((error) => {
            console.log(error);
        });
    }
}

export default {
    state,
    getters,
    mutations,
    actions
}