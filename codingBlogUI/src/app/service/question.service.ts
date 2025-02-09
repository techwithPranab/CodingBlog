
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Answer {
  answerId: string;
  content: string;
  author: string;
  votes: number;
}

export interface Question {
  id: string;
  title: string;
  description: string;
  author: string;
  tags: string[];
  answers: Answer[];
}

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = 'http://localhost:8080/api/questions';

  constructor(private http: HttpClient) {}

  getAllQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(this.apiUrl);
  }

  createQuestion(question: Question): Observable<Question> {
    return this.http.post<Question>(this.apiUrl, question);
  }

  getQuestion(id: string): Observable<Question> {
    return this.http.get<Question>(`${this.apiUrl}/${id}`);
  }

  addAnswer(questionId: string, answer: Answer): Observable<Question> {
    return this.http.post<Question>(`${this.apiUrl}/${questionId}/answers`, answer);
  }

  voteAnswer(questionId: string, answerId: string, upvote: boolean): Observable<Question> {
    return this.http.post<Question>(`${this.apiUrl}/${questionId}/answers/${answerId}/vote?upvote=${upvote}`, {});
  }
}
