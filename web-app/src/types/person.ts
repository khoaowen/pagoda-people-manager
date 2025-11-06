export interface PersonDTO {
  id?: string
  lastName: string
  firstName: string
  gender: string
  birthDate: string
  birthPlace: string
  permanentResidence: string
  temporaryResidence: string
  hometown: string
  nationality: string
  ethnicity: string
  dharmaName: string
  ordinationDate: string
  idNumber: string
  idIssueDate: string
  idIssuePlace: string
  email: string
  phoneNumber: string
  notes: string
  type: PersonType
}

export type PersonType = 'BUDDHIST' | 'LAY_BROTHER' | 'MASTER'
export type Gender = 'MALE' | 'FEMALE'
