import {useEffect, useState} from 'react'
import type {PersonDTO} from './types/person'
import {api} from './services/api'
import PersonForm from './components/PersonForm'
import PeopleList from './components/PeopleList'
import './App.css'

function App() {
    const [people, setPeople] = useState<PersonDTO[]>([])
    const [showForm, setShowForm] = useState(false)
    const [searchName, setSearchName] = useState('')

    useEffect(() => {
        loadPeople()
    }, [])

    const loadPeople = async (name?: string) => {
        const data = await api.getAll(name)
        setPeople(data)
    }

    const handleCreate = async (person: PersonDTO) => {
        await api.create(person)
        setShowForm(false)
        loadPeople()
    }

    const handleSearch = () => {
        loadPeople(searchName)
    }

    return (
        <div style={{padding: 20, maxWidth: 1200, margin: '0 auto'}}>
            <h1>🙏 Quản lý Phật tử</h1>

            <div style={{marginBottom: 20, display: 'flex', gap: 10}}>
                <input
                    type="text"
                    placeholder="Tìm theo tên..."
                    value={searchName}
                    onChange={(e) => setSearchName(e.target.value)}
                    style={{padding: 8, flex: 1}}
                />
                <button onClick={handleSearch} style={{padding: '8px 16px', cursor: 'pointer'}}>
                    🔍 Tìm
                </button>
                <button onClick={() => loadPeople()} style={{padding: '8px 16px', cursor: 'pointer'}}>
                    🔄 Tất cả
                </button>
                <button onClick={() => setShowForm(!showForm)} style={{padding: '8px 16px', cursor: 'pointer'}}>
                    {showForm ? '❌ Đóng' : '➕ Thêm mới'}
                </button>
                <button onClick={() => api.downloadListPdf()} style={{padding: '8px 16px', cursor: 'pointer'}}>
                    📋 Xuất PDF
                </button>
            </div>

            {showForm && <PersonForm onSubmit={handleCreate}/>}

            <PeopleList people={people} onDownloadPdf={api.downloadPdf}/>
        </div>
    )
}

export default App
