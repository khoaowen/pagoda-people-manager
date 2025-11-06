import { test, expect } from '@playwright/test'
import { randomUUID } from 'crypto'

const randomSuffix = () => randomUUID().slice(0, 8)
const unique = (prefix: string) => `${prefix}-${randomSuffix()}`
const uniqueEmail = () => `user-${randomSuffix()}@example.com`

test.describe('Person Management', () => {
  
  test('should load the app', async ({ page }) => {
    await page.goto('/')
    await expect(page.locator('h1')).toContainText('Quản lý Phật tử')
  })

  test('should add a new person', async ({ page }) => {
    await page.goto('/')
    
    // Click add button
    const lastName = unique('Nguyễn')
    const firstName = unique('VănA')

    await page.click('button:has-text("Thêm mới")')
    
    // Fill form
    await page.fill('input[name="lastName"]', lastName)
    await page.fill('input[name="firstName"]', firstName)
    await page.selectOption('select[name="gender"]', 'MALE')
    await page.fill('input[name="dharmaName"]', unique('ThiệnTâm'))
    await page.selectOption('select[name="type"]', 'BUDDHIST')
    await page.fill('input[name="email"]', uniqueEmail())
    
    // Submit
    await page.click('button[type="submit"]')
    
    // Verify person appears in list
    await expect(page.locator('table')).toContainText(`${lastName} ${firstName}`)
  })

  test('should search for person', async ({ page }) => {
    await page.goto('/')
    
    // Add a person first
    const lastName = unique('Trần')
    const firstName = unique('ThịB')
    const dharmaName = unique('DiệuÂm')

    await page.click('button:has-text("Thêm mới")')
    await page.fill('input[name="lastName"]', lastName)
    await page.fill('input[name="firstName"]', firstName)
    await page.fill('input[name="dharmaName"]', dharmaName)
    await page.click('button[type="submit"]')
    
    // Search
    await page.fill('input[placeholder*="Tìm"]', lastName)
    await page.click('button:has-text("Tìm")')
    
    // Verify results
    await expect(page.locator('table')).toContainText(`${lastName} ${firstName}`)
  })

  test('should download PDF', async ({ page }) => {
    await page.goto('/')
    
    // Add a person
    const lastName = unique('Lê')
    const firstName = unique('VănC')

    await page.click('button:has-text("Thêm mới")')
    await page.fill('input[name="lastName"]', lastName)
    await page.fill('input[name="firstName"]', firstName)
    await page.click('button[type="submit"]')
    
    // Wait for person to appear
    await expect(page.locator('table')).toContainText(`${lastName} ${firstName}`)
    
    // Click PDF button - opens new tab with PDF (we just verify button works)
    await page.click('button:has-text("PDF")')
    await page.waitForTimeout(500)
  })

  test('should download full list PDF', async ({ page }) => {
    await page.goto('/')
    
    // Click export PDF button - opens new tab with PDF (we just verify button works)
    await page.click('button:has-text("Xuất PDF")')
    await page.waitForTimeout(500)
  })
})
