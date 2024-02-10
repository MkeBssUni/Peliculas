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
            ],
            movies: {}

        }
    },
    mounted() {
        instance.get("/movies/").then((response) => {
            this.movies = response.data.data;
        }).catch((error) => {
            alert("Error al cargar las películas");
        });
    }
}
</script>

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
                    <b-col v-for="movie in movies" :key="movie.id" cols="3" md="3" class="mt-3">
                        <b-card class="shadow-sm mb-2" :title="movie.name" :img-src="movie.image" img-alt="Image" img-top
                            tag="article" style="max-width: 20rem; min-height: 15rem;"
                            :sub-title="movie.category.name"
                            onmouseover="this.style.transform='scale(1.1)'" onmouseout="this.style.transform='scale(1)'">
                            <b-card-text>
                                {{ movie.description }}
                            </b-card-text>
                            <template #footer>
                                <p> <b-icon icon="calendar-date"></b-icon>
                                    Fecha de estreno: <strong>{{ movie.releaseDate }}</strong></p>
                            </template>
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
                            <b-form-input id="name" type="text" v-model="form.name"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6">
                        <b-form-group label="Año de estreno:" label-for="releaseDate">
                            <b-form-input id="releaseDate" type="number" v-model="form.releaseDate"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12">
                        <b-form-group label-for="category" class="mt-3 input-select">
                            <b-form-select id="category" v-model="form.category" :options="options"
                                style="width: 100%;"></b-form-select>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12">
                        <b-form-group label="Sinopsis:" label-for="description" class="mt-3">
                            <b-form-textarea id="description" v-model="form.description"></b-form-textarea>
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
