FROM node:18.18.0

WORKDIR /frontend

COPY package*.json ./

RUN npm ci

COPY . .

CMD ["npm", "run", "dev"]