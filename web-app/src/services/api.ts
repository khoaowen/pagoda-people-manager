import type {PersonDTO} from '../types/person'

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/persons'

export const api = {
    async getAll(name?: string): Promise<PersonDTO[]> {
        const url = name ? `${API_URL}?name=${encodeURIComponent(name)}` : API_URL
        const response = await fetch(url)
        return response.json()
    },

    async create(person: PersonDTO): Promise<PersonDTO> {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(person)
        })
        return response.json()
    },

    async update(id: string, person: PersonDTO): Promise<PersonDTO> {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(person)
        })
        return response.json()
    },

    downloadPdf(id: string) {
        window.open(`${API_URL}/${id}/report`, '_blank')
    },

    downloadListPdf() {
        window.open(`${API_URL}/report`, '_blank')
    }
}
