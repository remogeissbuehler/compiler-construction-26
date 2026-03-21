package statements

const DEFAULT_CAPACITY = 8

type Block struct {
	children []Statement
}

func NewBlock() *Block {
	return &Block{
		children: make([]Statement, 0, DEFAULT_CAPACITY),
	}
}

func NewBlockWith(children ...Statement) *Block {
	return &Block{
		children: children,
	}
}

func (s *Block) Add(st Statement) {
	s.children = append(s.children, st)
}

func (s *Block) Children() []Statement {
	return s.children
}

func (s *Block) Accept(v Visitor) {
	for _, child := range s.children {
		child.Accept(v)
	}
}
