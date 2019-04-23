import Vue from 'vue';
const state = {
    senders: []
}

const getters = {
    getSenders (state) {
        return state.senders;
    }
}

const mutations = {
    LOAD_SENDERS (state, senders) {
        state.senders = senders;
    },

    DELETE_SENDER (state, sender) {
        state.senders = state.senders.filter(s => s.spidId != sender.spidId);
    },

    UPDATE_SENDER (state, sender) {
        Vue.set(state.senders, state.senders.findIndex(s => s.spidId == sender.spidId), sender);
    },

    INSERT_SENDER (state, sender) {
        state.senders.push(sender);
    }
}

const actions = {
    LOAD_SENDERS ( {commit}, search ) {
        return axios.get('/admin/sender', {
            "params": {
                ...(search ? {search: search}: {})
            }
        }).then((response) => {
            commit('LOAD_SENDERS', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    DELETE_SENDER ( {commit}, sender ) {
        return axios.delete('/admin/sender/' + sender.spidId, {

        }).then((response) => {
            if (response.status === 202) {
                commit('DELETE_SENDER', sender);
            }
        }).catch((error) => {
            console.log(error);
        });
    },

    UPDATE_SENDER ( {commit}, sender) {
        return axios.put('/admin/sender/' + sender.spidId, sender, {

        }).then((response) => {
            commit('UPDATE_SENDER', response.data);
        }).catch((error) => {
            console.log(error);
        });
    },

    INSERT_SENDER ( {commit}, sender) {
        return axios.post('/admin/sender', sender, {

        }).then((response) => {
            commit('INSERT_SENDER', response.data);
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