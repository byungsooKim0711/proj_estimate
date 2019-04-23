import Vue from 'vue';
const state = {
    venders: []
}

const getters = {
     getVenders (state) {
        return state.venders;
    }
}

const mutations = {
    LOAD_VENDERS (state, venders) {
        state.venders = venders;
    },

    DELETE_VENDER (state, vender) {
        state.venders = state.venders.filter(v => v.venderId != vender.venderId);
    },

    UPDATE_VENDER (state, vender) {
        Vue.set(state.venders, state.venders.findIndex(v => v.venderId == vender.venderId), vender);
    },

    INSERT_VENDER (state, vender) {
        state.venders.push(vender);
    }
}

const actions = {
    LOAD_VENDERS ( {commit}, search ) {
        return axios.get('/admin/vender', { 
            "params": {
                ...(search ? {search: search}: {})
            }
        }).then((response) => {
            commit('LOAD_VENDERS', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    DELETE_VENDER ( {commit}, vender ) {
        return axios.delete('/admin/vender/' + vender.venderId, {

        }).then((response) => {
            if (response.status === 202) {
                commit('DELETE_VENDER', vender);
            }
        }).catch((error) => {
            console.log(error);
        });
    },

    UPDATE_VENDER ( {commit}, vender) {
        return axios.put('/admin/vender/' + vender.venderId, vender, {

        }).then((response) => {
            commit('UPDATE_VENDER', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    INSERT_VENDER ( {commit}, vender) {
        return axios.post('/admin/vender', vender, {

        }).then((response) => {
            commit('INSERT_VENDER', response.data);
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