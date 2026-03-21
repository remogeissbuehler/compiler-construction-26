package option

type Option[T any] struct {
	val    T
	exists bool
}

func Some[T any](val T) Option[T] {
	return Option[T]{val, true}
}

func None[T any]() Option[T] {
	var t T

	return Option[T]{t, false}
}

func (opt Option[T]) OrElse(alt T) T {
	if opt.exists {
		return opt.val
	}

	return alt
}

func (opt Option[T]) IsSome() bool {
	return opt.exists
}

func (opt Option[T]) IsNone() bool {
	return !opt.exists
}

func Map[T, S any](opt Option[T], f func(T) S) Option[S] {
	if opt.exists {
		return Some(f(opt.val))
	}

	return None[S]()
}

func (opt Option[T]) IsSomeAnd(predicate func(T) bool) bool {
	if !opt.exists {
		return false
	}

	return predicate(opt.val)
}

func (opt Option[T]) IfSomeThen(action func(T)) {
	if !opt.exists {
		return
	}

	action(opt.val)
}
