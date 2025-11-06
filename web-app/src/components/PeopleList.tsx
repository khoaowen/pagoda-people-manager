import type {PersonDTO} from '../types/person'

interface Props {
    people: PersonDTO[]
    onDownloadPdf: (id: string) => void
}

export default function PeopleList({people, onDownloadPdf}: Props) {
    const translateType = (type: string) => {
        switch (type) {
            case 'BUDDHIST':
                return 'Phật tử'
            case 'LAY_BROTHER':
                return 'Chú tiểu'
            case 'MASTER':
                return 'Sư thầy'
            default:
                return type
        }
    }

    return (
        <div>
            <h2>Danh sách ({people.length})</h2>
            {people.length === 0 ? (
                <p>Chưa có dữ liệu</p>
            ) : (
                <table style={{width: '100%', borderCollapse: 'collapse'}}>
                    <thead>
                    <tr style={{borderBottom: '2px solid #ddd'}}>
                        <th style={{padding: 10, textAlign: 'left'}}>Họ tên</th>
                        <th style={{padding: 10, textAlign: 'left'}}>Pháp danh</th>
                        <th style={{padding: 10, textAlign: 'left'}}>Loại</th>
                        <th style={{padding: 10, textAlign: 'left'}}>Email</th>
                        <th style={{padding: 10, textAlign: 'left'}}>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    {people.map(person => (
                        <tr key={person.id} style={{borderBottom: '1px solid #eee'}}>
                            <td style={{padding: 10}}>{person.lastName} {person.firstName}</td>
                            <td style={{padding: 10}}>{person.dharmaName}</td>
                            <td style={{padding: 10}}>{translateType(person.type)}</td>
                            <td style={{padding: 10}}>{person.email}</td>
                            <td style={{padding: 10}}>
                                <button onClick={() => onDownloadPdf(person.id!)} style={{cursor: 'pointer'}}>
                                    📄 PDF
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    )
}
