<script>
import instance from '../../../config/axios';
export default {
    data() {
        return {
            form: {
                name: '',
                releaseDate: null,
                category: {
                    id: null
                },
                description: '',
            },
            options: [],
            movies: {},
            errorMessages: {
                name: '',
                releaseDate: '',
                category: '',
                description: ''
            },
            showErrors: {
                name: false,
                releaseDate: false,
                category: false,
                description: false
            }
        }
    },
    methods: {
        notThisYear(year) {
            return year <= new Date().getFullYear()
        },
        validateInput(input) {
            const { name, releaseDate, category, description } = this.form;
            const { errorMessages, showErrors } = this;
            switch (input) {
                case 'name':
                    const input = document.getElementById('name');
                    if (name.length < 5) {
                        input.classList.add('is-invalid');
                        errorMessages.name = 'El nombre es requerido';
                        showErrors.name = true;
                    } else {
                        input.classList.remove('is-invalid');
                        input.classList.add('is-valid');
                        errorMessages.name = '';
                        showErrors.name = false;
                    }
                    break;

                case 'releaseDate':
                    const inputRelease = document.getElementById('releaseDate');
                    if (releaseDate == 0) {
                        inputRelease.classList.add('is-invalid');
                        errorMessages.releaseDate = 'El año de estreno es requerido';
                        showErrors.releaseDate = true;
                    } else if (!this.notThisYear(releaseDate)) {
                        inputRelease.classList.add('is-invalid');
                        errorMessages.releaseDate = 'El año de estreno no puede ser mayor al año actual';
                        showErrors.releaseDate = true;
                    } else {
                        inputRelease.classList.remove('is-invalid');
                        inputRelease.classList.add('is-valid');
                        errorMessages.releaseDate = '';
                        showErrors.releaseDate = false;
                    }
                    break;

                case 'category':
                    const inputCategory = document.getElementById('category');
                    if (category.id == null) {
                        inputCategory.classList.add('is-invalid');
                        errorMessages.category = 'La categoría es requerida';
                        showErrors.category = true;
                    } else {
                        inputCategory.classList.remove('is-invalid');
                        errorMessages.category = '';
                        showErrors.category = false;
                    }
                    break;

                case 'description':
                    const inputDescription = document.getElementById('description');
                    if (description == '') {
                        inputDescription.classList.add('is-invalid');
                        errorMessages.description = 'La descripción es requerida';
                        showErrors.description = true;
                    } else if (description.length < 10) {
                        inputDescription.classList.add('is-invalid');
                        errorMessages.description = 'La descripción debe tener al menos 10 caracteres';
                        showErrors.description = true;
                    } else {
                        inputDescription.classList.remove('is-invalid');
                        inputDescription.classList.add('is-valid');
                        errorMessages.description = '';
                        showErrors.description = false;
                    }
                    break;
            }
        },
        getMovies() {
            instance.get("/movies/").then((response) => {
                this.movies = response.data.data;
            }).catch((error) => {
                alert("Error al cargar las películas");
            });
        },
        getCategories() {
            instance.get("/categories/").then((response) => {
                this.options = response.data.data;
            }).catch((error) => {
                alert("Error al cargar las categorías");
            });
        },
        addMovie(){
            instance.post("/movies/", this.form).then((response) => {
                this.getMovies();
            }).catch((error) => {
                alert("Error al agregar la película");
            });
        }
    },
    mounted() {
        this.getMovies();
        this.getCategories();
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
                            tag="article" style="max-width: 20rem; min-height: 15rem;" :sub-title="movie.category.name"
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
                        <b-form-group label="Título:" label-for="name" :state="!showErrors.name"
                            :invalid-feedback="errorMessages.name">
                            <b-form-input id="name" type="text" v-model.trim="form.name"
                                @input="validateInput('name')"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6">
                        <b-form-group label="Año de estreno:" label-for="releaseDate" :state="!showErrors.releaseDate" :invalid-feedback="errorMessages.releaseDate">
                            <b-form-input @input="validateInput('releaseDate')" id="releaseDate" type="number" v-model.trim="form.releaseDate"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12">
                        <b-form-group label-for="category" class="mt-3 input-select">
                            <b-form-select id="category" v-model.trim="form.category.id"
                                @input="validateInput('category')"
                                :state="!showErrors.category" :invalid-feedback="errorMessages.category"
                                :options="options.map(option => ({ text: option.name, value: option.id }))"
                                style="width: 100%;"></b-form-select>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12">
                        <b-form-group label="Sinopsis:" label-for="description" class="mt-3">
                            <b-form-textarea id="description"
                                @input="validateInput('description')" :state="!showErrors.description" :invalid-feedback="errorMessages.description"
                            v-model.trim="form.description"></b-form-textarea>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6">
                        <b-button class="mt-3" variant="outline-danger" type="reset" style="width: 100%;">Limpiar</b-button>
                    </b-col>
                    <b-col cols="6">
                        <b-button block class="mt-3" variant="outline-success" type="submit"
                            @click="addMovie()" :disabled="(showErrors.name || showErrors.releaseDate || showErrors.category || showErrors.description)"
                            style="width: 100%;">Agregar</b-button>
                    </b-col>
                </b-row>
            </b-form>
        </b-modal>
    </b-container>
</template>
