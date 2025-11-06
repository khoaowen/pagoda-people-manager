import {type FormEvent, useState} from 'react'
import type {PersonDTO} from '../types/person'

interface Props {
    onSubmit: (person: PersonDTO) => void
    initialData?: PersonDTO
}

export default function PersonForm({onSubmit, initialData}: Props) {
    const [person, setPerson] = useState<PersonDTO>(initialData || {
        lastName: '',
        firstName: '',
        gender: 'MALE',
        birthDate: '',
        birthPlace: '',
        permanentResidence: '',
        temporaryResidence: '',
        hometown: '',
        nationality: 'Việt Nam',
        ethnicity: 'Kinh',
        dharmaName: '',
        ordinationDate: '',
        idNumber: '',
        idIssueDate: '',
        idIssuePlace: '',
        email: '',
        phoneNumber: '',
        notes: '',
        type: 'BUDDHIST'
    })

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault()
        onSubmit(person)
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        setPerson({...person, [e.target.name]: e.target.value})
    }

    return (
        <form onSubmit={handleSubmit} style={{maxWidth: 600, margin: '0 auto'}}>
            <h2>{initialData ? 'Chỉnh sửa' : 'Thêm người mới'}</h2>

            <div style={{marginBottom: 10}}>
                <label>Họ: </label>
                <input name="lastName" value={person.lastName} onChange={handleChange} required/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Tên: </label>
                <input name="firstName" value={person.firstName} onChange={handleChange} required/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Giới tính: </label>
                <select name="gender" value={person.gender} onChange={handleChange}>
                    <option value="MALE">Nam</option>
                    <option value="FEMALE">Nữ</option>
                </select>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Ngày sinh: </label>
                <input type="date" name="birthDate" value={person.birthDate} onChange={handleChange}/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Nơi sinh: </label>
                <input name="birthPlace" value={person.birthPlace} onChange={handleChange}/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Pháp danh: </label>
                <input name="dharmaName" value={person.dharmaName} onChange={handleChange}/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Ngày xuất gia: </label>
                <input type="date" name="ordinationDate" value={person.ordinationDate} onChange={handleChange}/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Loại: </label>
                <select name="type" value={person.type} onChange={handleChange}>
                    <option value="BUDDHIST">Phật tử</option>
                    <option value="LAY_BROTHER">Chú tiểu</option>
                    <option value="MASTER">Sư thầy</option>
                </select>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Email: </label>
                <input type="email" name="email" value={person.email} onChange={handleChange}/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Số điện thoại: </label>
                <input name="phoneNumber" value={person.phoneNumber} onChange={handleChange}/>
            </div>

            <div style={{marginBottom: 10}}>
                <label>Ghi chú: </label>
                <textarea name="notes" value={person.notes} onChange={handleChange} rows={3}/>
            </div>

            <button type="submit" style={{padding: '10px 20px', cursor: 'pointer'}}>
                {initialData ? 'Cập nhật' : 'Thêm'}
            </button>
        </form>
    )
}
