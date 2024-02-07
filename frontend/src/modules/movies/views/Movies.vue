<template>
    <b-container fluid class="px-3">
        <b-row>
            <b-col cols="12" md="9" class="mt-5">
                <h4>Películas</h4>
            </b-col>
            <b-col cols="12" md="3" class="mt-3 mt-md-5">
                <b-button v-b-modal.modal-1 variant="outline-success" class="position-right">
                    Agregar nueva película
                    <b-icon icon="plus-circle"></b-icon>
                </b-button>
            </b-col>
        </b-row>
        <b-row>
            <b-col cols="12">
                <b-row>
                    <b-col cols="12" md="3" class="mt-3">
                        <b-card title="Card Title" img-src="https://picsum.photos/600/300/?image=25" img-alt="Image" img-top
                            tag="article" style="max-width: 20rem;" class="mb-2">
                            <b-card-text>
                                Some quick example text to build on the card title and make up the bulk of the card's
                                content.
                            </b-card-text>
                            <b-button href="#" variant="primary">Go somewhere</b-button>
                        </b-card>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>
        <b-modal hide-footer id="modal-1" title="Agregar nueva película">
            <b-form>
                <b-row>
                    <b-col cols="6">
                        <b-form-group label="Título:" label-for="name">
                            <b-form-input id="name" type="text" v-model="name"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6">
                        <b-form-group label="Año de estreno:" label-for="releaseDate">
                            <b-form-input id="releaseDate" type="number" v-model="releaseDate"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12">
                        <b-form-group label-for="category" class="mt-3 input-select">
                            <b-form-select id="category" v-model="category" :options="options"
                                style="width: 100%;"></b-form-select>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12">
                        <b-form-group label="Sinopsis:" label-for="description" class="mt-3">
                            <b-form-textarea id="description" v-model="description"></b-form-textarea>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6">
                        <b-button block class="mt-3" variant="outline-success" type="submit"
                            style="width: 100%;">Agregar</b-button>
                    </b-col>
                    <b-col cols="6">
                        <b-button class="mt-3" variant="outline-danger" type="reset" style="width: 100%;">Limpiar</b-button>
                    </b-col>
                </b-row>
            </b-form>
        </b-modal>
    </b-container>
</template>

<script>
import instance from '../../../config/axios';
export default {
    data() {
        return {
            form: {
                name: '',
                releaseDate: null,
                category: '',
                description: ''
            },
            options: [
                { value: '', text: 'Selecciona una categoría' },
                { value: 'Drama', text: 'Drama' },
                { value: 'Terror', text: 'Terror' }
            ]
        }
    },
    mounted(){
        instance.get("/movies/").then((response) => {
            console.log(response);
        }).catch((error) => {
            console.log(error);
        });
    }
}
</script>

<style scoped></style>