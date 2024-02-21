<template>
    <div>
        <form @submit.prevent="submitForm">
            <label for="name">Nombre: </label>
            <input type="text" id="name" v-model="formData.name" required>

            <br />

            <div class="frc-captcha" ref="container" data-sitekey="FCMH277K7PN4HR59" data-lang="es">
            </div>
            <button type="submit">Enviar</button>
        </form>
    </div>
</template>

<script>

import { WidgetInstance } from "friendly-challenge";
import instance from "../config/axios";
import { ref } from "vue";

const container = ref();
const widget = ref();

export default {
    data() {
        return {
            container: ref(),
            widget: ref(),
            formData: {
                name: ""
            }
        }
    },
    methods: {
        async doneCallback(solution) {
            console.log('Captcha was solved. The form can be submitted.');
            console.log(solution);
            let response = await this.verifyCaptcha(solution);
            console.log("respuesta del captcha", response);
        },

        errorCallback(err) {
            console.log('There was an error when trying to solve the Captcha.');
            console.log(err);
        },
        verifyCaptcha(solution) {
            return instance.post("/captcha/verify?solution=" + solution)
                .then(response => response.data)
                .catch(error => {
                    console.error('Error al verificar el captcha:', error);
                    throw error; // Re-lanzar el error para que el componente que llama pueda manejarlo
                });
        }
    },
    mounted() {
        if (this.$refs.container) {
            widget.value = new WidgetInstance(this.$refs.container, {
                startMode: "auto",
                doneCallback: this.doneCallback,
                errorCallback: this.errorCallback
            });
        }
    },
    beforeDestroy() {
        if (this.widget) {
            this.widget.destroy();
        }
    }
}
</script>

<style></style>