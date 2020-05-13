import { BaseClass } from '../base-class'

import { Authority } from './authority'

export class User extends BaseClass {
  name!: string
  email!: string
  username!: string
  password!: string
  active!: boolean

  authorities!: Authority[]

  constructor() {
    super()
    this.active = true
  }

  public isActive(): boolean {
    return this.active
  }
}
