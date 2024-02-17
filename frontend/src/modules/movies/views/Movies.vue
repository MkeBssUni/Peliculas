<script>
import instance from '../../../config/axios';

export default {
    data() {
        return {
            form: {
                name: '',
                director: '',
                releaseDate: null,
                category: {
                    id: ''
                },
                description: '',
            },
            filterDto: {
                name: '',
                director: '',
                startYear: null,
                endYear: null,
                category: '',
                order: 'default'
            },
            options: [],
            movies: {},
            errorMessages: {
                name: '',
                director: '',
                directorForm: '',
                releaseDateForm: '',
                category: '',
                categoryForm: '',
                descriptionForm: '',
                startYear: '',
                endYear: ''
            },
            showErrors: {
                nameForm: false,
                director: false,
                directorForm: false,
                releaseDateForm: false,
                category: false,
                categoryForm: false,
                descriptionForm: false,
                startYear: false,
                endYear: false
            },
            showFilters: true
        }
    },
    methods: {
        notThisYear(year) {
            return year <= new Date().getFullYear()
        },
        validateInput(input) {
            const { name, releaseDate, category, description, director } = this.form;
            const { startYear, endYear } = this.filterDto;
            const { errorMessages, showErrors } = this;
            switch (input) {
                case 'name':
                    const input = document.getElementById('nameForm');
                    if (name.length < 5) {
                        input.classList.add('is-invalid');
                        errorMessages.nameForm = 'El nombre es requerido';
                        showErrors.nameForm = true;
                    } else {
                        input.classList.remove('is-invalid');
                        input.classList.add('is-valid');
                        errorMessages.nameForm = '';
                        showErrors.nameForm = false;
                    }
                    break;

                case 'description':
                    const inputDescription = document.getElementById('descriptionForm');
                    if (description.length < 10) {
                        inputDescription.classList.add('is-invalid');
                        errorMessages.descriptionForm = 'La descripción debe tener al menos 10 caracteres';
                        showErrors.descriptionForm = true;
                    } else {
                        inputDescription.classList.remove('is-invalid');
                        inputDescription.classList.add('is-valid');
                        errorMessages.descriptionForm = '';
                        showErrors.descriptionForm = false;
                    }
                    break;

                case 'director':
                    const inputDirector = document.getElementById('directorForm');
                    if (director.length < 5) {
                        inputDirector.classList.add('is-invalid');
                        errorMessages.directorForm = 'El director es requerido';
                        showErrors.directorForm = true;
                    } else {
                        inputDirector.classList.remove('is-invalid');
                        inputDirector.classList.add('is-valid');
                        errorMessages.directorForm = '';
                        showErrors.directorForm = false;
                    }
                    break;

                case 'releaseDate':
                    const inputRelease = document.getElementById('releaseDateForm');
                    if (releaseDate == '') {
                        inputRelease.classList.add('is-invalid');
                        errorMessages.releaseDateForm = 'El año de estreno es requerido';
                        showErrors.releaseDateForm = true;
                    } else if (!this.notThisYear(releaseDate)) {
                        inputRelease.classList.add('is-invalid');
                        errorMessages.releaseDateForm = 'El año de estreno no puede ser mayor al año actual';
                        showErrors.releaseDateForm = true;
                    } else if (releaseDate < 0) {
                        inputRelease.classList.add('is-invalid');
                        errorMessages.releaseDateForm = 'El año de estreno no puede ser negativo';
                        showErrors.releaseDateForm = true;
                    } else {
                        inputRelease.classList.remove('is-invalid');
                        inputRelease.classList.add('is-valid');
                        errorMessages.releaseDateForm = '';
                        showErrors.releaseDateForm = false;
                    }
                    break;
                case 'startYear':
                    const inputStartYear = document.getElementById('startYear');
                    if (!this.notThisYear(startYear)) {
                        inputStartYear.classList.add('is-invalid');
                        errorMessages.startYear = 'El año de inicio no puede ser mayor al año actual';
                        showErrors.startYear = true;
                    } else if (startYear < 0) {
                        inputStartYear.classList.add('is-invalid');
                        errorMessages.startYear = 'El año de inicio no puede ser negativo';
                        showErrors.startYear = true;
                    } else {
                        inputStartYear.classList.remove('is-invalid');
                        errorMessages.startYear = '';
                        showErrors.startYear = false;
                    }
                    break;

                case 'endYear':
                    const inputEndYear = document.getElementById('endYear');
                    if (!this.notThisYear(endYear)) {
                        inputEndYear.classList.add('is-invalid');
                        errorMessages.endYear = 'El año de fin no puede ser mayor al año actual';
                        showErrors.endYear = true;
                    } else if (endYear < 0) {
                        inputEndYear.classList.add('is-invalid');
                        errorMessages.endYear = 'El año de fin no puede ser negativo';
                        showErrors.endYear = true;
                    } else if (endYear < startYear) {
                        inputEndYear.classList.add('is-invalid');
                        errorMessages.endYear = 'El año de fin no puede ser menor al año de inicio';
                        showErrors.endYear = true;

                    } else {
                        inputEndYear.classList.remove('is-invalid');
                        errorMessages.endYear = '';
                        showErrors.endYear = false;
                    }
                    break;

                case 'category':
                    const inputCategory = document.getElementById('categoryForm');
                    if (category.id == '') {
                        inputCategory.classList.add('is-invalid');
                        errorMessages.categoryForm = 'La categoría es requerida';
                        showErrors.categoryForm = true;
                    } else {
                        inputCategory.classList.remove('is-invalid');
                        errorMessages.category = '';
                        showErrors.category = false;
                    }
                    break;
            }
        },
        getMovies() {
            instance.post("/movies/all", this.filterDto).then((response) => {
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
        addMovie() {
            this.validateInput('name');
            this.validateInput('releaseDate');
            this.validateInput('category');
            this.validateInput('description');
            this.validateInput('director');

            if (this.showErrors.name || this.showErrors.releaseDate || this.showErrors.category || this.showErrors.description || this.showErrors.directorForm || (this.form.category.id == '')) return;

            instance.post("/movies/", this.form).then((response) => {
                this.getMovies();
            }).catch((error) => {
                alert("Error al agregar la película");
            });
        },
        cleanFilters() {
            this.filterDto = {
                name: '',
                director: '',
                startYear: null,
                endYear: null,
                category: "",
                order: 'default'
            }
            this.getMovies();
        },
        resetForm() {
            this.form = {
                name: '',
                director: '',
                releaseDate: null,
                category: {
                    id: ''
                },
                description: '',
            }
        },
        handleDrop(event) {
            event.preventDefault();
            this.addMovie();
        },
        handleScroll() {
            if (window.scrollY > 30) {
                this.showFilters = false;
            } else {
                this.showFilters = true;
            }
        }
    },
    mounted() {
        window.addEventListener("scroll", this.handleScroll);
        this.getMovies();
        this.getCategories();
    },
    beforeDestroy() {
        window.removeEventListener("scroll", this.handleScroll);
    }
}
</script>

<template>
    <b-container fluid class="px-5">
        <b-row>
            <b-col cols="12" class="mt-5 px-3">
                <b-form>
                    <b-card v-show="showFilters">
                        <template #header>
                            <h5 class="mb-0" id="header-filters">Filtros avanzados</h5>
                        </template>
                        <b-row>
                            <b-col cols="12" md="4">
                                <b-form-group label="Buscar por nombre:" label-for="name">
                                    <b-form-input id="name" type="text" v-model.trim="filterDto.name"
                                        @keyup.enter="getMovies()"></b-form-input>
                                </b-form-group>
                                <b-form-group label="Buscar por director:" label-for="director" class="mt-3">
                                    <b-form-input id="director" type="text" v-model.trim="filterDto.director"
                                        @keyup.enter="getMovies()"></b-form-input>
                                </b-form-group>
                            </b-col>
                            <b-col cols="12" md="4">
                                <b-form-group label="Entre el año:" label-for="startYear" class="mt-3 mt-md-0"
                                    :state="!showErrors.startYear" :invalid-feedback="errorMessages.startYear">
                                    <b-form-input id="startYear" @input="validateInput('startYear')" type="number"
                                        v-model.trim="filterDto.startYear"></b-form-input>
                                </b-form-group>
                                <b-form-group label="Y el año:" label-for="endYear" class="mt-3"
                                    :state="!showErrors.endYear" :invalid-feedback="errorMessages.endYear">
                                    <b-form-input id="endYear" @input="validateInput('endYear')" type="number"
                                        v-model.trim="filterDto.endYear"></b-form-input>
                                </b-form-group>
                            </b-col>
                            <b-col cols="12" md="4">
                                <b-form-group label="Buscar por categoría:" label-for="category" class="mt-3 mt-md-0">
                                    <b-form-select id="category" v-model.trim="filterDto.category" class="input-select">
                                        <option value="">Todas las categorías</option>
                                        <option v-for="option in options" :key="option.id" :value="option.id">
                                            {{ option.name }}
                                        </option>
                                    </b-form-select>
                                </b-form-group>
                                <b-form-group label="Ordenar por:" label-for="order" class="mt-3">
                                    <b-form-select id="order" v-model.trim="filterDto.order"
                                        :options="[{ text: 'Predeterminado', value: 'default' }, { text: 'Ascendente', value: 'asc' }, { text: 'Descendente', value: 'desc' }]"
                                        class="input-select">
                                    </b-form-select>
                                </b-form-group>
                            </b-col>
                            <b-col cols="6" md="4" class="mt-3">
                                <b-button variant="outline-primary" @click="getMovies()" style="width: 100%;"
                                    :disabled="showErrors.startYear || showErrors.endYear"
                                    class="d-flex justify-content-between">
                                    Buscar y aplicar filtros
                                    <b-icon icon="search"></b-icon>
                                </b-button>
                            </b-col>
                            <b-col cols="6" md="4" class="mt-3">
                                <b-button variant="outline-danger" type="reset" @click="cleanFilters()" style="width: 100%;"
                                    class="d-flex justify-content-between">
                                    Limpiar
                                    <b-icon icon="trash"></b-icon>
                                </b-button>
                            </b-col>
                        </b-row>
                    </b-card>
                </b-form>
            </b-col>
        </b-row>
        <b-row>
            <b-col cols="12" md="8">
                <h3 class="mt-5">Catálogo de películas disponibles</h3>
            </b-col>
            <b-col cols="12" md="4" class="mt-3 mt-md-5">
                <b-button variant="outline-success" @click="$bvModal.show('modal-1')" style="width: 100%;"
                    class="d-flex justify-content-between">
                    Agregar película
                    <b-icon icon="plus-circle"></b-icon>
                </b-button>
            </b-col>
        </b-row>
        <b-row>
            <b-col cols="8">
                <div class="drag-container" style="background-color: #f9f9f9; border-radius: 8px;" @dragover.prevent
                    @drop="handleDrop">
                    <b-row class="m-1">
                        <b-col v-for="movie in movies" :key="movie.id" cols="12" md="6" lg="4" class="mt-5">
                                <b-card class="shadow-sm mb-2" :title="movie.name" :key="movie.id"
                                    onmouseover="this.style.transform='scale(1.1)'"
                                    onmouseout="this.style.transform='scale(1)'" style="height: 18rem;">
                                    <h6>{{ movie.director }}</h6>
                                    <b-card-sub-title>
                                        {{ movie.category.name }}
                                    </b-card-sub-title>
                                    <b-card-text class="mt-2">
                                        {{ movie.description }}
                                    </b-card-text>
                                    <template #footer>
                                        <p>
                                            <b-icon icon="calendar"></b-icon>
                                            Fecha de estreno: <strong>{{ movie.releaseDate }}</strong>
                                        </p>
                                    </template>
                                </b-card>
                        </b-col>
                        <b-col cols="12" class="my-5 d-flex justify-content-center" v-if="movies.length == 0">
                            <img src="../../../assets/noMovies.png" alt="Imágenes no encontradas" class="img">
                        </b-col>
                    </b-row>
                </div>
            </b-col>
            <b-col cols="4">
                <b-card class="mt-5 p-3">
                    <b-card-title class="text-center">Agregar nueva película</b-card-title>
                    <p class="text-center my-3" style="color: gray; font-size: smaller;">
                        (Llena correctamente los campos para agregar una nueva película y arrástrala al catálogo)
                    </p>
                    <b-form>
                        <b-card draggable="true" class="drag-item">
                            <b-row>
                                <b-col cols="12">
                                    <b-form-group label="Título:" label-for="nameForm" :state="!showErrors.name"
                                        :invalid-feedback="errorMessages.name">
                                        <b-form-input id="nameForm" type="text" v-model.trim="form.name"
                                            @input="validateInput('name')"></b-form-input>
                                    </b-form-group>
                                </b-col>
                                <b-col cols="12" class="mt-2">
                                    <b-form-group label="Director:" label-for="directorForm"
                                        :state="!showErrors.directorForm" :invalid-feedback="errorMessages.directorForm">
                                        <b-form-input @input="validateInput('director')" id="directorForm" type="text"
                                            v-model.trim="form.director"></b-form-input>
                                    </b-form-group>
                                </b-col>
                                <b-col cols="12" class="mt-2">
                                    <b-form-group label="Año de estreno:" label-for="releaseDateForm"
                                        :state="!showErrors.releaseDateForm"
                                        :invalid-feedback="errorMessages.releaseDateForm">
                                        <b-form-input @input="validateInput('releaseDate')" id="releaseDateForm"
                                            type="number" v-model.trim="form.releaseDate"></b-form-input>
                                    </b-form-group>
                                </b-col>
                                <b-col cols="12" class="mt-4">
                                    <b-form-group label-for="categoryForm">
                                        <b-form-select id="categoryForm" v-model.trim="form.category.id"
                                            :state="!showErrors.category" :invalid-feedback="errorMessages.category"
                                            @change="validateInput('category')"
                                            :options="options.map(option => ({ text: option.name, value: option.id }))"
                                            class="input-select">
                                            <option value="" disabled>Selecciona una categoría...</option>
                                        </b-form-select>
                                    </b-form-group>
                                </b-col>
                                <b-col cols="12" class="mt-4">
                                    <b-form-group label="Sinopsis:" label-for="descriptionForm"
                                        :state="!showErrors.descriptionForm"
                                        :invalid-feedback="errorMessages.descriptionForm">
                                        <b-form-textarea id="descriptionForm" @input="validateInput('description')"
                                            v-model.trim="form.description"></b-form-textarea>
                                    </b-form-group>
                                </b-col>
                            </b-row>
                        </b-card>
                    </b-form>
                </b-card>
            </b-col>
        </b-row>
        <b-modal hide-footer id="modal-1" title="Agregar nueva película">
            <b-form>
                <b-row>
                    <b-col cols="12">
                        <b-form-group label="Título:" label-for="nameForm" :state="!showErrors.name"
                            :invalid-feedback="errorMessages.name">
                            <b-form-input id="nameForm" type="text" v-model.trim="form.name"
                                @input="validateInput('name')"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-2">
                        <b-form-group label="Director:" label-for="directorForm" :state="!showErrors.directorForm"
                            :invalid-feedback="errorMessages.directorForm">
                            <b-form-input @input="validateInput('director')" id="directorForm" type="text"
                                v-model.trim="form.director"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-2">
                        <b-form-group label="Año de estreno:" label-for="releaseDateForm"
                            :state="!showErrors.releaseDateForm" :invalid-feedback="errorMessages.releaseDateForm">
                            <b-form-input @input="validateInput('releaseDate')" id="releaseDateForm" type="number"
                                v-model.trim="form.releaseDate"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-4">
                        <b-form-group label-for="categoryForm">
                            <b-form-select id="categoryForm" v-model.trim="form.category.id" :state="!showErrors.category"
                                :invalid-feedback="errorMessages.category" @change="validateInput('category')"
                                :options="options.map(option => ({ text: option.name, value: option.id }))"
                                class="input-select">
                                <option value="" disabled>Selecciona una categoría...</option>
                            </b-form-select>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="mt-4">
                        <b-form-group label="Sinopsis:" label-for="descriptionForm" :state="!showErrors.descriptionForm"
                            :invalid-feedback="errorMessages.descriptionForm">
                            <b-form-textarea id="descriptionForm" @input="validateInput('description')"
                                v-model.trim="form.description"></b-form-textarea>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6">
                        <b-button class="mt-4 d-flex justify-content-between" variant="outline-danger" type="reset"
                            @click="resetForm()" style="width: 100%;">
                            Limpiar
                            <b-icon icon="trash" font-scale="1"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6">
                        <b-button block class="mt-4 d-flex justify-content-between" variant="outline-success" type="submit"
                            @click="addMovie()"
                            :disabled="(showErrors.name || showErrors.releaseDate || showErrors.category || showErrors.description || showErrors.directorForm || (form.category.id == ''))"
                            style="width: 100%;">
                            Agregar
                            <b-icon icon="plus-circle" font-scale="1"></b-icon>
                        </b-button>
                    </b-col>
                </b-row>
            </b-form>
        </b-modal>
    </b-container>
</template>

<style scoped>
.input-select {
    width: 100%;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    padding: 0.375rem 0.75rem;
    font-size: 1rem;
    line-height: 1.5;
    background-color: #fff;
    background-clip: padding-box;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.img {
    width: 360px;
}

.btn {
    width: 100%;
}
</style>
