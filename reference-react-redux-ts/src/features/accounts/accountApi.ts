// Path: src/features/accounts/accountApi.ts

export enum AccountStatus {
    OPEN = 'OPEN',
    CLOSED = 'CLOSED'
}

export interface Account {
    id: number;
    name: string;
    number: string;
    openDate: string;
    closeDate: string;
    status: AccountStatus;
}


export class AccountApi {
    private readonly baseUrl: string;

    constructor(baseUrl: string) {
        this.baseUrl = baseUrl;
    }

    async getAllAccounts(): Promise<Account[]> {
        const response = await fetch(`${this.baseUrl}/accounts`);
        return await response.json();
    }

    async getAccount(id: number): Promise<Account> {
        const response = await fetch(`${this.baseUrl}/accounts/${id}`);
        return await response.json();
    }

    async createAccount(account: Account): Promise<Account> {
        const response = await fetch(`${this.baseUrl}/accounts`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(account)
        });
        return await response.json();
    }

    async updateAccount(account: Account): Promise<Account> {
        const response = await fetch(`${this.baseUrl}/accounts`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(account)
        });
        return await response.json();
    }

    async deleteAccount(id: number): Promise<void> {
        await fetch(`${this.baseUrl}/accounts/${id}`, {
            method: 'DELETE'
        });
    }
}


